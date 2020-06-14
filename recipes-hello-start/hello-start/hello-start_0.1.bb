SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
TARGET_CC_ARCH += "${LDFLAGS}"

inherit update-rc.d
INITSCRIPT_NAME = "hello-start-script"
INITSCRIPT_PARAMS = "defaults 90 10"


SRC_URI = "file://hello-start.c"
SRC_URI += "file://hello-start-script"

S = "${WORKDIR}"

do_compile() {
	    ${CC} hello-start.c -o hello-start
}

do_install() {
	    install -d ${D}${bindir}
	    install -m 0755 hello-start ${D}${bindir}

        # Install startup script
	    install -d ${D}/etc/init.d
	    install -m 0755 hello-start-script ${D}/etc/init.d
}

# Add the startup script to the list of files to be packaged.  Any files
# that are installed but not packaged will cause a warning to be printed
# during the bitbake process.
FILES_${PN} += "/etc/init.d/*"

