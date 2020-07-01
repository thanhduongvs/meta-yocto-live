require recipes-core/images/core-image-minimal.bb

DESCRIPTION = "A small image just containing a calculator"

#IMAGE_INSTALL += " bc"

IMAGE_INSTALL += "hello"
IMAGE_INSTALL += "hello-start"
IMAGE_INSTALL += "hello-patch"
#IMAGE_INSTALL += " this-is"
IMAGE_INSTALL += "libanswer"

