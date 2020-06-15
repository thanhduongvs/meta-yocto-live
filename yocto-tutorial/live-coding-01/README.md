# Live Coding with Yocto Project #01 download and first build

Link video [#01 download and first build](https://www.youtube.com/watch?v=EfKLrSxA_H8 "Youtube")

## 1. Tải file source code:
- 💻 *$* `sudo apt-get install gawk wget git diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev xterm`
  - 📌 Cài đặt các tool để hỗ trợ build yocto
- 💻 *$* `mkdir yocto`
  - 📌 Tạo thư mục để chứa source code
- 💻 *$* `cd yocto`
- 💻 *yocto$* `git clone https://git.yoctoproject.org/git/poky.git`
  - 📌 Đây là source code của Yocto Project
- 💻 *$* `cd poky`
- 💻 *yocto/poky$* `git checkout yocto-2.6.4`
  - 📌 Ở ví dụ này mình dùng branch yocto-2.6.4
  - ℹ️ Câu lệnh sẽ khác nếu dùng yocto project nhỏ hơn 2.4
- 💻 *yocto/poky$* `vim meta-poky/conf/distro/poky.conf`
  - 📌 Mở file **poky.conf** để xem version yocto hiện tại có hỗ trợ build trên bản ubuntu đang dùng
  - 📌 Nhìn vào bên dưới sẽ thấy 2 bản ubuntu hỗ trợ build branch yocto-2.6.4 là ubuntu-16.04 và ubuntu-18.04
  ```
    SANITY_TESTED_DISTROS ?= " \
            poky-2.5 \n \
            poky-2.6 \n \
            ubuntu-16.04 \n \
            ubuntu-18.04 \n \
            fedora-28 \n \
            centos-7 \n \
            debian-8 \n \
            debian-9 \n \
            opensuse-42.3 \n \
            "
  ```

## 2. Build yocto:
- 💻 *yocto$* `source poky/oe-init-build-env build`
  - 📌 thiết lập các biến môi trường
  - 📌 Chỉnh sửa file **yocto/build/conf/local.conf**
  - 📌 Thay thế **MACHINE ??= "qemux86"** thành **MACHINE ??= "qemuarm"**
  - 📌 Sau đó chạy lại lệnh *yocto$* `source poky/oe-init-build-env`
- 💻 *yocto/build$* `bitbake core-image-minimal --runall=fetch`
  - 📌 Bước này yocto sẽ tải về các package phục vụ cho việc buid
  - ℹ️ Đối với yocto nhỏ hơn 2.4 câu lệnh trên được thay thế bằng `bitbake -c fetchall core-image-minimal`
- 💻 *yocto/build$* `bitbake -k core-image-minimal`
  - 📌 Bước này yocto sẽ build image
- ℹ️ Lệnh `bitbake core-image-minimal` có thể thay thế  2 bước `bitbake core-image-minimal --runall=fetch` và `bitbake -k core-image-minimal`. 
Nhưng tách ra để khi có lỗi xảy ra, dễ phân biệt lỗi do lúc download package hay lúc build image

## 3. Chạy máy ảo:
- 💻 *yocto/build$* `runqemu qemuarm`
  - ℹ️ Sau đó nhập mật khẩu của máy tính của bạn
  - ℹ️ Để đăng nhập máy ảo gõ `root`
  - ℹ️ Có thể chạy qemu trên terminal `runqemu qemuarm nographic`, Để thoát gõ lệnh <kbd>Ctrl + A</kbd> <kbd>X</kbd>
