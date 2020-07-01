# Live Coding with Yocto Project #03 package dependencies and splitting

Link video [#03 package dependencies and splitting](https://www.youtube.com/watch?v=IehnEC3GOGU "Youtube")

- 💻 *yocto$* `source poky/oe-init-build-env`
- 💻 *yocto/build$* `cd ../`
- Tạo thư mục **recipes-libanswer** như bên dưới:
    ```
    recipes-libanswer
       └── libanswer
           └── libanswer_0.1.bb
    ```
- File **libanswer_0.1.bb** như bên dưới:
    ```
    # Recipe created by recipetool
    # This is the basis of a recipe and may need further editing in order to be fully functional.
    # (Feel free to remove these comments when editing.)

    # WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
    # your responsibility to verify that the values are complete and correct.
    LICENSE = "MIT"
    LIC_FILES_CHKSUM = "file://LICENSE;md5=9273579e85f231c463bf432ce84c2479"

    SRC_URI = "git://github.com/LetoThe2nd/libanswer.git;protocol=https"

    # Modify these as desired
    PV = "1.0+git${SRCPV}"
    SRCREV = "78a62700aa6a5a48499316a4ef965e5b2e8d2908"

    S = "${WORKDIR}/git"

    inherit cmake

    # Specify any options you want to pass to cmake using EXTRA_OECMAKE:
    EXTRA_OECMAKE = ""
    ```
- Mở file **yocto-live/meta-yocto-live/recipes-core/images/core-image-live.bb**
  - Nếu có dùng *IMAGE_INSTALL += " bc"* thì comment lại thành *#IMAGE_INSTALL += " bc"*
    - Nếu có dùng *IMAGE_INSTALL += " boots"* thì comment lại thành *#IMAGE_INSTALL += " boots"*
- 💻 *yocto$* `bitbake libanswer`
  - Sẽ báo lỗi **Unable to find the requested Boost libraries**
  - Khi đó mở file *libanswer_0.1.bb* thêm dòng **DEPENDS = "boost"**
  - Chạy lại lệnh `bitbake libanswer`
- 💻 *yocto$* `bitbake core-image-live`
- 💻 *yocto$* `runqemu qemuarm core-image-live nographic`
- 💻 *root@qemuarm:~#* `ask`
  - Khi đó báo lỗi **sh: bc: not found**
  - Thoát máy ảo qemuarm
  - Khi đó mở file *libanswer_0.1.bb* thêm dòng **RDEPENDS_${PN} = "bc"**
  - Chạy lại lệnh `bitbake libanswer`
  - Chạy lại lệnh `bitbake core-image-live`
  - Chạy lại lệnh `runqemu qemuarm core-image-live nographic`
  - Chạy lại lệnh `ask`
- Đến lúc này ta cần phân biệt **DEPENDS** và **RDEPENDS**
  - **DEPENDS**: Build time package dependencies. Những gói package cần thiết cho việc build như ví dụ trên là *"boost"*
  - **RDEPENDS**: Run time package dependencies. Những gói package cần thiết cho việc run như ví dụ trên là *"bc"*