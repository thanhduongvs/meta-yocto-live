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

DEPENDS = "boost"
RDEPENDS_${PN} = "bc"

inherit cmake

PACKAGES =+ "${PN}-example"

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = ""