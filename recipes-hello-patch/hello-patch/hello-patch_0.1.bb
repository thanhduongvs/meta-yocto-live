SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
TARGET_CC_ARCH += "${LDFLAGS}"

SRC_URI = "file://hello-patch.c"
SRC_URI += "file://0001-hello.patch"

S = "${WORKDIR}"

do_compile() {
	    ${CC} hello-patch.c -o hello-patch
}

do_install() {
	    install -d ${D}${bindir}
	    install -m 0755 hello-patch ${D}${bindir}
}

python do_build() {
    bb.plain("***********************************************");
    bb.plain("*                                             *");
    bb.plain("*  Example recipe created by bitbake-layers   *");
    bb.plain("*                                             *");
    bb.plain("***********************************************");
}
