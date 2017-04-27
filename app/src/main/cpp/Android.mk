
MY_LOCAL_PATH := $(call my-dir)

#### libiconv
include $(CLEAR_VARS)
LOCAL_MODULE := libiconv

LOCAL_PATH := $(MY_LOCAL_PATH)/libiconv

LOCAL_CFLAGS := \
  -Wno-multichar \
  -DAndroid \
  -DLIBDIR="c" \
  -DBUILDING_LIBICONV \
  -DIN_LIBRARY

LOCAL_SRC_FILES := \
  libcharset/lib/localcharset.c \
  lib/iconv.c \
  lib/relocatable.c

LOCAL_C_INCLUDES := \
	 $(LOCAL_PATH)/include \
 	 $(LOCAL_PATH)/lib \
	 $(LOCAL_PATH)/include

include $(BUILD_SHARED_LIBRARY)


## libzbarjni
include $(CLEAR_VARS)

LOCAL_PATH := $(MY_LOCAL_PATH)
LOCAL_MODULE := zbarjni
LOCAL_SRC_FILES := zbarjni.c \
		   zbar/img_scanner.c \
		   zbar/decoder.c \
		   zbar/image.c \
		   zbar/symbol.c \
		   zbar/convert.c \
 	       zbar/config.c \
		   zbar/scanner.c \
		   zbar/error.c \
		   zbar/refcnt.c \
		   zbar/video.c \
		   zbar/video/null.c \
		   zbar/decoder/code128.c \
		   zbar/decoder/code39.c \
		   zbar/decoder/code93.c \
		   zbar/decoder/codabar.c \
		   zbar/decoder/databar.c \
		   zbar/decoder/ean.c \
	  	   zbar/decoder/i25.c \
		   zbar/decoder/qr_finder.c \
		   zbar/qrcode/bch15_5.c \
		   zbar/qrcode/binarize.c \
		   zbar/qrcode/isaac.c \
		   zbar/qrcode/qrdec.c \
		   zbar/qrcode/qrdectxt.c \
		   zbar/qrcode/rs.c \
		   zbar/qrcode/util.c

LOCAL_C_INCLUDES := $(MY_LOCAL_PATH)/include \
		    $(MY_LOCAL_PATH)/zbar \
		    $(MY_LOCAL_PATH)/libiconv/include
		    
LOCAL_SHARED_LIBRARIES := libiconv

include $(BUILD_SHARED_LIBRARY)