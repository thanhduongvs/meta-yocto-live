# Live Coding with Yocto Project #02 simple layer, custom image and devtool

Link video [#02 simple layer, custom image and devtool](https://www.youtube.com/watch?v=nqHylLP2NmA "Youtube")

## 1. Tạo 1 layer mới:
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

## 2. Đẩy **meta-yocto-live** lên github:
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

## 3. Tạo custom image
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

## 4. Tạo recipes mẫu:
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
- File **hello.c** xem ở đây: [hello.c](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello/hello/hello-0.1/hello.c "Github")
- File **hello_0.1.bb** xem ở đây: [hello_0.1.bb](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello/hello/hello_0.1.bb "Github")
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Thêm dòng **IMAGE_INSTALL += " hello"** ở cuối. Để thêm *recipes-hello* vừa tạo vào *core-image-live*
- 💻 *yocto/poky/build$* `bitbake hello`
- 💻 *yocto/poky/build$* `bitbake core-image-live`
- 💻 *yocto/build$* `runqemu qemuarm core-image-live nographic`
- 💻 *root@qemuarm:~#* `hello` 
  - 📌 Để chạy ví dụ hello vừa build

## 5. Tạo recipes mẫu khi image bắt đầu:
- Tạo thư mục **recipes-hello-start** có cấu trúc như bên dưới:
    ```
    meta-yocto-live
    └── hello-start
        ├── hello-start-0.1
        │   ├── hello-start.c
        │   └── hello-start-script
        └── hello-start_0.1.bb
    ```
- File **hello-start.c** xem ở đây: [hello-start.c](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello-start/hello-start/hello-start-0.1/hello-start.c "Github")
- File **hello-start-script** xem ở đây: [hello-start-script](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello-start/hello-start/hello-start-0.1/hello-start-script "Github")
- File **hello-start_0.1.bb** xem ở đây: [hello-start_0.1.bb](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello-start/hello-start/hello-start_0.1.bb "Github")
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Thêm dòng **IMAGE_INSTALL += " hello-start"** ở cuối
- 💻 *yocto/poky/build$* `bitbake hello-start`
- 💻 *yocto/poky/build$* `bitbake core-image-live`
- 💻 *yocto/build$* `runqemu qemuarm core-image-live`
- 📌 Khi khởi động xong trước khi gõ `root` để đăng nhập, hello-start sẽ được chạy
  ![hello-start](https://raw.githubusercontent.com/thanhduongvs/meta-yocto-live/yocto-2.6.4/yocto-tutorial/live-coding-02/hello-start.png)

## 6. Tạo recipes với patch file:
- Tạo thư mục **recipes-hello-patch** có cấu trúc như bên dưới:
    ```
    recipes-hello-patch
    └── hello-patch
        ├── after
        │   └── hello-patch.c
        ├── before
        │   └── hello-patch.c
        ├── hello-patch-0.1
        │   ├── 0001-hello.patch
        │   └── hello-patch.c
        └── hello-patch_0.1.bb
    ```
- File **0001-hello.patch** xe được tạo sau, không nên tạo trước
- File **hello-patch.c** xem ở đây: [hello-patch.c](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello-patch/hello-patch/hello-patch-0.1/hello-patch.c "Github")
  - 📌 Cả 3 file hello-patch.c giống nhau
- File **hello-patch_0.1.bb** xem ở đây: [hello-patch_0.1.bb](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello-patch/hello-patch/hello-patch_0.1.bb "Github")
- Mở file **after/hello-patch.c** đổi dòng `printf("Hello word!\n");` thành `printf("Hello Van Son!\n");`
  - 📌 Xem kết quả ở đây ở đây: [hello-patch_0.1.bb](https://github.com/thanhduongvs/meta-yocto-live/blob/yocto-2.6.4/recipes-hello-patch/hello-patch/after/hello-patch.c "Github")
- 💻 *yocto/meta-yocto-live/recipes-hello-patch/hello-patch$* `diff -rupN before/hello-patch.c after/hello-patch.c > 0001-hello.patch`
  - 📌 Kết quả sẽ tạo file `0001-hello.patch`
  - 📌 Copy file `0001-hello.patch` vào thư mục `hello-patch-0.1`
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Thêm dòng **IMAGE_INSTALL += " hello-patch"** ở cuối
- 💻 *yocto/poky/build$* `bitbake hello-patch`
- 💻 *yocto/poky/build$* `bitbake core-image-live`
- 💻 *yocto/build$* `runqemu qemuarm core-image-live`
- 💻 *root@qemuarm:~#* `hello-patch`
  - 📌 Sau khi lệnh thực thi sẽ in ra dòng **Hello Van Son!** chứ không phải **Hello word!**

## 7. Dùng devtool
- 💻 *yocto$* `devtool create-workspace meta-yocto-live/recipes-devtool`
  - 📌 Tạo workspace cho devtool nằm ở thư mục recipes-devtool
  - 📌 Nếu không dungf lệnh này mặc định devtool sẽ nằm ở thư mục yocto/build
- 💻 *yocto$* `devtool add https://github.com/LetoThe2nd/this_is.git`
  - 📌 devtool sẽ tạo recipes this-is ở đây `yocto/meta-yocto-live/recipes-devtool/recipes/this-is`
  - 📌 Try cập vào https://github.com/LetoThe2nd/this_is ta thấy có rất nhiều file, nhưng trong **recipes-devtool/recipes/this-is** chỉ có 1 file **this-is_git.bb**
  - 📌 Hiểu đơn giản thì devtool sẽ làm đơn giản hóa công việc của người code thay vì get suorce về
- 💻 *yocto$* `vim meta-yocto-live/recipes-core/images/core-image-live.bb`
  - 📌 Thêm dòng **IMAGE_INSTALL += " this-is"** ở cuối
- 💻 *yocto/poky/build$* `bitbake this-is`
- 💻 *yocto/poky/build$* `bitbake core-image-live`
- 💻 *yocto/build$* `runqemu qemuarm core-image-live`
- 💻 *root@qemuarm:~#* `this_is`