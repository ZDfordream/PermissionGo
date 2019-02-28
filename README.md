# PermissionGo

PermissionGo 是一个轻量级的权限库，适配国内厂商。经过亿级下载量app检验

## 依赖

dependencies {
    implementation 'com.zhudong:PermissionGo:1.0.2'
}

## 使用

PermissionGo.requestPermissions(MainActivity.this, new PermissionsCallback() {

            @Override
            public void onAcceptAll() {
            }

            @Override
            public void onAcceptPart(List<String> partAcceptedPermissions) {
            }

            @Override
            public void onRefused(List<String> refusedPermissions) {
            }

            @Override
            public void onNeverShowed(List<String> neverShowedPermissions) {
            }

        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION);

## 欢迎star