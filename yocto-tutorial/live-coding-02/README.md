# Live Coding with Yocto Project #02 simple layer, custom image and devtool

Link video [#02 simple layer, custom image and devtool](https://www.youtube.com/watch?v=nqHylLP2NmA&t=811s "Youtube")

1. Tạo 1 layer mới:
- 💻 *yocto$* `source poky/oe-init-build-env`
- 💻 *yocto/build$* `cd ../`
- 💻 *yocto$* `bitbake-layers create-layer meta-yocto-live`
- 💻 *yocto$* `vim poky/build/conf/bblayers.conf`
  - 📌 Thêm dòng **/home/vanson/working/yocto/meta-yocto-live \\** là đường dẫn tuyệt đối của layer *meta-yocto-live* vừa mới tạo, để  khi build yocto, yocto sẽ build layer bạn vừa tạo
  - 📌 Kết quả như bên dưới
  ```
    BBLAYERS ?= " \
        /home/vanson/working/yocto/poky/meta \
        /home/vanson/working/yocto/poky/meta-poky \
        /home/vanson/working/yocto/poky/meta-yocto-bsp \
        /home/vanson/working/yocto/meta-yocto-live \
    "
  ```
- 💻 *yocto$* `bitbake-layers show-layers`
  - 📌 sẽ hiện thị tất cả các layer trong yocto
- 💻 *yocto$* `bitbake core-image-minimal`
  - 📌 Sau khi chạy lệnh ở trên hãy để ý dòng **meta-yocto-live = "\<unknown>:\<unknown>"**

2. Đẩy **meta-yocto-live** lên github:
- 💻 *yocto$* `cd meta-yocto-live`
- 💻 *yocto/meta-yocto-live$* `git init`
- 💻 *yocto/meta-yocto-live$* `git add .`
- 💻 *yocto/meta-yocto-live$* `git commit -m 'Initial version'`
- 💻 *yocto/meta-yocto-live$* `git remote add origin https://github.com/thanhduongvs/meta-yocto-live.git`
- 💻 *yocto/meta-yocto-live$* `git push -u origin master`
- 💻 *yocto/meta-yocto-live$* `git branch yocto-2.6.4`
  - 📌 Tên nhánh nên trùng với tên nhánh của yocto đã chọn lúc ban đầu
- 💻 *yocto/meta-yocto-live$* `git push origin yocto-2.6.4`
- 💻 *yocto/meta-yocto-live$* `git checkout yocto-2.6.4`
- 💻 *yocto$* `bitbake core-image-minimal`
  - 📌 Sau khi chạy lệnh ở trên hãy để ý dòng **meta-yocto-live = "yocto-2.6.4:ca9c344696d5b77a962acfceab34cc2d070ced2a"**
  - 📌 Đó là sự khác biệt sau khi add git

3. Tạo custom image
- 💻 *yocto$* `mkdir -p meta-yocto-live/recipes-core/images`
- 💻 *yocto$* `cp poky/meta/recipes-core/images/core-image-minimal-dev.bb meta-yocto-live/recipes-core/images/core-image-live.bb`
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Chỉnh sửa lại như bên dưới:
  ```
    require recipes-core/images/core-image-minimal.bb

    DESCRIPTION = "A small image just containing a calculator"

    IMAGE_INSTALL += " bc"
    IMAGE_FEATURES += " ssh-server-dropbear"
  ```
- 💻 *yocto/poky/build$* `bitbake core-image-live`
- 💻 *yocto/build$* `runqemu qemuarm core-image-live nographic`
- 💻 *root@qemuarm:~#* `bc`
  - 📌 Sẽ hiện thông tin gói bc đã thêm trong file `core-image-live`

4. Tạo recipes mẫu:
- Tạo thư mục **recipes-hello** có cấu trúc như bên dưới:
    ```
    meta-yocto-live
    ├── recipes-core
    │   └── images
    │       └── core-image-live.bb
    └── recipes-hello
        └── hello
            ├── hello-0.1
            │   └── hello.c
            └── hello_0.1.bb
    ```
- File **hello.c** như sau:
    ```C
    #include <stdio.h>

    int main(int argc, char **argv)
    {
        printf("Hello word, this is \"%s\"\n", argv[0]);

        return 0;
    }
    ```
- File **hello_0.1.bb** như sau:
    ```C
    SUMMARY = "bitbake-layers recipe"
    DESCRIPTION = "Recipe created by bitbake-layers"
    LICENSE = "MIT"
    LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
    TARGET_CC_ARCH += "${LDFLAGS}"
    SRC_URI = "file://hello.c"

    S = "${WORKDIR}"

    do_compile() {
            ${CC} hello.c -o hello
    }

    do_install() {
            install -d ${D}${bindir}
            install -m 0755 hello ${D}${bindir}
    }

    python do_build() {
        bb.plain("***********************************************");
        bb.plain("*                                             *");
        bb.plain("*  Example recipe created by bitbake-layers   *");
        bb.plain("*                                             *");
        bb.plain("***********************************************");
    }

    ```
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Thêm dòng **IMAGE_INSTALL += " hello"** ở cuối. Để thêm *recipes-hello* vừa tạo vào *core-image-live*
- 💻 *yocto/poky/build$* `bitbake hello`
- 💻 *yocto/poky/build$* `bitbake core-image-live`

5. Tạo recipes mẫu khi image bắt đầu:
- Tạo thư mục **recipes-hello-start** có cấu trúc như bên dưới:
    ```
    meta-yocto-live
    └── hello-start
        ├── hello-start-0.1
        │   ├── hello-start.c
        │   └── hello-start-script
        └── hello-start_0.1.bb
    ```
- File **hello.c** như sau:
    ```C
    #include <stdio.h>

    int main(int argc, char **argv)
    {
        printf("Hello word, this is \"%s\"\n", argv[0]);

        return 0;
    }
    ```
- File **hello_0.1.bb** như sau:
    ```C
    SUMMARY = "bitbake-layers recipe"
    DESCRIPTION = "Recipe created by bitbake-layers"
    LICENSE = "MIT"
    LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
    TARGET_CC_ARCH += "${LDFLAGS}"
    SRC_URI = "file://hello.c"

    S = "${WORKDIR}"

    do_compile() {
            ${CC} hello.c -o hello
    }

    do_install() {
            install -d ${D}${bindir}
            install -m 0755 hello ${D}${bindir}
    }

    python do_build() {
        bb.plain("***********************************************");
        bb.plain("*                                             *");
        bb.plain("*  Example recipe created by bitbake-layers   *");
        bb.plain("*                                             *");
        bb.plain("***********************************************");
    }

    ```
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Thêm dòng **IMAGE_INSTALL += " hello"** ở cuối. Để thêm *recipes-hello* vừa tạo vào *core-image-live*
- 💻 *yocto/poky/build$* `bitbake hello`
- 💻 *yocto/poky/build$* `bitbake core-image-live`