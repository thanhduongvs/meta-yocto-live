require recipes-core/images/core-image-minimal.bb

DESCRIPTION = "A small image just containing a calculator"

IMAGE_INSTALL += "bc"

IMAGE_INSTALL += "hello"

