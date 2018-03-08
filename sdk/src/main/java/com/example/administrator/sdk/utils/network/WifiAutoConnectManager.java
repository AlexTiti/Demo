package com.example.administrator.sdk.utils.network;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.example.administrator.sdk.content.Constent;
import com.example.administrator.sdk.utils.StringUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class WifiAutoConnectManager {

    private static final String TAG = WifiAutoConnectManager.class
            .getSimpleName();

    final WifiManager wifiManager;

    /**
     *  // 定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
     */
    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    /**
     *
     * @param wifiManager
     */
    public WifiAutoConnectManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    /**
     * 提供一个外部接口，传入要连接的无线网
     * @param ssid
     * @param password
     * @param type
     */
    public void connect(String ssid, String password, WifiCipherType type) {
        Thread thread = new Thread(new ConnectRunnable(ssid, password, type));
        thread.start();
    }

    /**
     * 查看以前是否也配置过这个网络
     * @param SSID
     * @return
     */
    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = wifiManager
                .getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    private WifiConfiguration createWifiInfo(String SSID, String password,
                                             WifiCipherType type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        if (type == WifiCipherType.WIFICIPHER_NOPASS) {

            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        }

        if (type == WifiCipherType.WIFICIPHER_WEP) {
            if (!StringUtils.isEmpty(password)) {
                if (isHexWepKey(password)) {
                    config.wepKeys[0] = password;
                } else {
                    config.wepKeys[0] = "\"" + password + "\"";
                }
            }
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (type == WifiCipherType.WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);

            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;

        }
        return config;
    }

    /**
     * 打开Wifi
     * @return
     */
    private void openWifi() {

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭WIFI
     */
    private void closeWifi() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    class ConnectRunnable implements Runnable {
        private final String ssid;

        private final String password;

        private final WifiCipherType type;

        public ConnectRunnable(String ssid, String password, WifiCipherType type) {
            this.ssid = ssid;
            this.password = password;
            this.type = type;
        }

        @Override
        public void run() {
            // 打开wifi
            openWifi();
            // 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
            // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
            while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                try {
                    // 为了避免程序一直while循环，让它睡个100毫秒检测……
                    Thread.sleep(100);

                } catch (InterruptedException ignored) {
                }
            }
            WifiConfiguration tempConfig = isExsits(ssid);
            if (tempConfig != null) {
                boolean b = wifiManager.enableNetwork(tempConfig.networkId,
                        true);
            } else {
                WifiConfiguration wifiConfig = createWifiInfo(ssid, password,
                        type);
                if (wifiConfig == null) {
                    return;
                }
                int netID = wifiManager.addNetwork(wifiConfig);
                boolean enabled = wifiManager.enableNetwork(netID, true);
                boolean connected = wifiManager.reconnect();
            }
        }
    }

    private static boolean isHexWepKey(String wepKey) {
        final int len = wepKey.length();
        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        return !(len != Constent.WIFI_TEN && len != Constent.WIFI_TWENTY_SIX && len != Constent.WIFI_FIVE_EIGHT) && isHex(wepKey);

    }

    private static boolean isHex(String key) {
        for (int i = key.length() - 1; i >= 0; i--) {
            final char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
                    && c <= 'f')) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取ssid的加密方式
     * @param context
     * @param ssid
     * @return
     */
    public static WifiCipherType getCipherType(Context context, String ssid) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        List<ScanResult> list = wifiManager != null ? wifiManager.getScanResults() : null;

        for (ScanResult scResult : list) {

            if (!StringUtils.isEmpty(scResult.SSID) && scResult.SSID.equals(ssid)) {
                String capabilities = scResult.capabilities;

                if (!StringUtils.isEmpty(capabilities)) {

                    if (capabilities.contains("WPA")
                            || capabilities.contains("wpa")) {
                        return WifiCipherType.WIFICIPHER_WPA;
                    } else if (capabilities.contains("WEP")
                            || capabilities.contains("wep")) {
                        return WifiCipherType.WIFICIPHER_WEP;
                    } else {
                        return WifiCipherType.WIFICIPHER_NOPASS;
                    }
                }
            }
        }
        return WifiCipherType.WIFICIPHER_INVALID;
    }
}