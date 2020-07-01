# Live Coding with Yocto Project #04 SDKs

Link video [#04 SDKs](https://www.youtube.com/watch?v=u1rzYRz83kc "Youtube")

## 1. Thêm gcc
- 💻 *yocto$* `source poky/oe-init-build-env`
- 💻 *yocto/build$* `cd ../`
- 💻 *yocto$* `bitbake core-image-live`
- 💻 *yocto$* `runqemu qemuarm core-image-live nographic`
- 💻 *root@qemuarm:~#* `touch main.c`. file c
    ```c
    #include<stdio.h>

    int main(void){
        printf("Hello\n");
        return 0;
    }
    ```
- 💻 *root@qemuarm:~#* `gcc main.c`
  - Báo lỗi **-sh: gcc: not found**
  - Thoát máy ảo
- Mở file **yocto/build/conf/local.conf**
  - Tìm dòng *EXTRA_IMAGE_FEATURES* và thêm *tools-sdk* như bên dưới: `EXTRA_IMAGE_FEATURES ?= "debug-tweaks tools-sdk"`
- 💻 *yocto$* `bitbake core-image-live`
- 💻 *yocto$* `runqemu qemuarm core-image-live nographic`
- Tạo lại file main.c như trên

## 2. Build SDK
- Việc build file trên máy ảo (board) là không tốt. Ta sẽ build file thực thi từ sdk, sau đó truyền file thực thi qua máy ảo thông qua ssh
- Mở file **yocto/build/conf/local.conf**
  - Tìm dòng *EXTRA_IMAGE_FEATURES* và thêm *ssh-server-dropbear* và bỏ *tools-sdk* như bên dưới: `EXTRA_IMAGE_FEATURES ?= "debug-tweaks ssh-server-dropbear"`
- 💻 *yocto$* `bitbake core-image-live`
- 💻 *yocto$* `bitbake core-image-live -c populate_sdk`
- Sau khi build xong file sdk sẽ nằm ở thư mục **yocto/build/tmp/deploy/sdk**
- 💻 *yocto$* `cd build/tmp/deploy/sdk`
- 💻 *yocto$* `./poky-glibc-x86_64-core-image-live-armv5e-toolchain-2.6.4.sh`
  - Ở đây mình giải nén ra thư mục `yocto/sdk`

