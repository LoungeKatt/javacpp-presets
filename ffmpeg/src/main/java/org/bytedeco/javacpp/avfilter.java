// Targeted by JavaCPP version 0.8-2-SNAPSHOT

package org.bytedeco.javacpp;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.postproc.*;
import static org.bytedeco.javacpp.swresample.*;
import static org.bytedeco.javacpp.swscale.*;

public class avfilter extends org.bytedeco.javacpp.presets.avfilter {
    static { Loader.load(); }

// Parsed from <libavfilter/avfilter.h>

/*
 * filter layer
 * Copyright (c) 2007 Bobby Bingham
 *
 * This file is part of FFmpeg.
 *
 * FFmpeg is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * FFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with FFmpeg; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

// #ifndef AVFILTER_AVFILTER_H
// #define AVFILTER_AVFILTER_H

/**
 * @file
 * @ingroup lavfi
 * Main libavfilter public API header
 */

/**
 * @defgroup lavfi Libavfilter - graph-based frame editing library
 * @{
 */

// #include <stddef.h>

// #include "libavutil/attributes.h"
// #include "libavutil/avutil.h"
// #include "libavutil/dict.h"
// #include "libavutil/frame.h"
// #include "libavutil/log.h"
// #include "libavutil/samplefmt.h"
// #include "libavutil/pixfmt.h"
// #include "libavutil/rational.h"

// #include "libavfilter/version.h"

/**
 * Return the LIBAVFILTER_VERSION_INT constant.
 */
public static native @Cast("unsigned") int avfilter_version();

/**
 * Return the libavfilter build-time configuration.
 */
public static native @Cast("const char*") BytePointer avfilter_configuration();

/**
 * Return the libavfilter license.
 */
public static native @Cast("const char*") BytePointer avfilter_license();
@Opaque public static class AVFilterFormats extends Pointer {
    public AVFilterFormats() { }
    public AVFilterFormats(Pointer p) { super(p); }
}

// #if FF_API_AVFILTERBUFFER
/**
 * A reference-counted buffer data type used by the filter system. Filters
 * should not store pointers to this structure directly, but instead use the
 * AVFilterBufferRef structure below.
 */
public static class AVFilterBuffer extends Pointer {
    static { Loader.load(); }
    public AVFilterBuffer() { allocate(); }
    public AVFilterBuffer(int size) { allocateArray(size); }
    public AVFilterBuffer(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterBuffer position(int position) {
        return (AVFilterBuffer)super.position(position);
    }

    /** buffer data for each plane/channel */
    public native @Cast("uint8_t*") BytePointer data(int i); public native AVFilterBuffer data(int i, BytePointer data);
    @MemberGetter public native @Cast("uint8_t**") PointerPointer data();

    /**
     * pointers to the data planes/channels.
     *
     * For video, this should simply point to data[].
     *
     * For planar audio, each channel has a separate data pointer, and
     * linesize[0] contains the size of each channel buffer.
     * For packed audio, there is just one data pointer, and linesize[0]
     * contains the total size of the buffer for all channels.
     *
     * Note: Both data and extended_data will always be set, but for planar
     * audio with more channels that can fit in data, extended_data must be used
     * in order to access all channels.
     */
    public native @Cast("uint8_t*") BytePointer extended_data(int i); public native AVFilterBuffer extended_data(int i, BytePointer extended_data);
    @MemberGetter public native @Cast("uint8_t**") PointerPointer extended_data();
    /** number of bytes per line */
    public native int linesize(int i); public native AVFilterBuffer linesize(int i, int linesize);
    @MemberGetter public native IntPointer linesize();

    /** private data to be used by a custom free function */
    public native Pointer priv(); public native AVFilterBuffer priv(Pointer priv);
    /**
     * A pointer to the function to deallocate this buffer if the default
     * function is not sufficient. This could, for example, add the memory
     * back into a memory pool to be reused later without the overhead of
     * reallocating it from scratch.
     */
    public static class Free_AVFilterBuffer extends FunctionPointer {
        static { Loader.load(); }
        public    Free_AVFilterBuffer(Pointer p) { super(p); }
        protected Free_AVFilterBuffer() { allocate(); }
        private native void allocate();
        public native void call(AVFilterBuffer buf);
    }
    public native Free_AVFilterBuffer free(); public native AVFilterBuffer free(Free_AVFilterBuffer free);

    /** media format */
    public native int format(); public native AVFilterBuffer format(int format);
    /** width and height of the allocated buffer */
    public native int w(); public native AVFilterBuffer w(int w);
    public native int h(); public native AVFilterBuffer h(int h);
    /** number of references to this buffer */
    public native @Cast("unsigned") int refcount(); public native AVFilterBuffer refcount(int refcount);
}

/** can read from the buffer */
public static final int AV_PERM_READ =     0x01;
/** can write to the buffer */
public static final int AV_PERM_WRITE =    0x02;
/** nobody else can overwrite the buffer */
public static final int AV_PERM_PRESERVE = 0x04;
/** can output the buffer multiple times, with the same contents each time */
public static final int AV_PERM_REUSE =    0x08;
/** can output the buffer multiple times, modified each time */
public static final int AV_PERM_REUSE2 =   0x10;
/** the buffer requested can have negative linesizes */
public static final int AV_PERM_NEG_LINESIZES = 0x20;
/** the buffer must be aligned */
public static final int AV_PERM_ALIGN =    0x40;

public static final int AVFILTER_ALIGN = 16; //not part of ABI

/**
 * Audio specific properties in a reference to an AVFilterBuffer. Since
 * AVFilterBufferRef is common to different media formats, audio specific
 * per reference properties must be separated out.
 */
public static class AVFilterBufferRefAudioProps extends Pointer {
    static { Loader.load(); }
    public AVFilterBufferRefAudioProps() { allocate(); }
    public AVFilterBufferRefAudioProps(int size) { allocateArray(size); }
    public AVFilterBufferRefAudioProps(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterBufferRefAudioProps position(int position) {
        return (AVFilterBufferRefAudioProps)super.position(position);
    }

    /** channel layout of audio buffer */
    public native @Cast("uint64_t") long channel_layout(); public native AVFilterBufferRefAudioProps channel_layout(long channel_layout);
    /** number of audio samples per channel */
    public native int nb_samples(); public native AVFilterBufferRefAudioProps nb_samples(int nb_samples);
    /** audio buffer sample rate */
    public native int sample_rate(); public native AVFilterBufferRefAudioProps sample_rate(int sample_rate);
    /** number of channels (do not access directly) */
    public native int channels(); public native AVFilterBufferRefAudioProps channels(int channels);
}

/**
 * Video specific properties in a reference to an AVFilterBuffer. Since
 * AVFilterBufferRef is common to different media formats, video specific
 * per reference properties must be separated out.
 */
public static class AVFilterBufferRefVideoProps extends Pointer {
    static { Loader.load(); }
    public AVFilterBufferRefVideoProps() { allocate(); }
    public AVFilterBufferRefVideoProps(int size) { allocateArray(size); }
    public AVFilterBufferRefVideoProps(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterBufferRefVideoProps position(int position) {
        return (AVFilterBufferRefVideoProps)super.position(position);
    }

    /** image width */
    public native int w(); public native AVFilterBufferRefVideoProps w(int w);
    /** image height */
    public native int h(); public native AVFilterBufferRefVideoProps h(int h);
    /** sample aspect ratio */
    public native @ByRef AVRational sample_aspect_ratio(); public native AVFilterBufferRefVideoProps sample_aspect_ratio(AVRational sample_aspect_ratio);
    /** is frame interlaced */
    public native int interlaced(); public native AVFilterBufferRefVideoProps interlaced(int interlaced);
    /** field order */
    public native int top_field_first(); public native AVFilterBufferRefVideoProps top_field_first(int top_field_first);
    /** picture type of the frame */
    public native @Cast("AVPictureType") int pict_type(); public native AVFilterBufferRefVideoProps pict_type(int pict_type);
    /** 1 -> keyframe, 0-> not */
    public native int key_frame(); public native AVFilterBufferRefVideoProps key_frame(int key_frame);
    /** qp_table stride */
    public native int qp_table_linesize(); public native AVFilterBufferRefVideoProps qp_table_linesize(int qp_table_linesize);
    /** qp_table size */
    public native int qp_table_size(); public native AVFilterBufferRefVideoProps qp_table_size(int qp_table_size);
    /** array of Quantization Parameters */
    public native BytePointer qp_table(); public native AVFilterBufferRefVideoProps qp_table(BytePointer qp_table);
}

/**
 * A reference to an AVFilterBuffer. Since filters can manipulate the origin of
 * a buffer to, for example, crop image without any memcpy, the buffer origin
 * and dimensions are per-reference properties. Linesize is also useful for
 * image flipping, frame to field filters, etc, and so is also per-reference.
 *
 * TODO: add anything necessary for frame reordering
 */
public static class AVFilterBufferRef extends Pointer {
    static { Loader.load(); }
    public AVFilterBufferRef() { allocate(); }
    public AVFilterBufferRef(int size) { allocateArray(size); }
    public AVFilterBufferRef(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterBufferRef position(int position) {
        return (AVFilterBufferRef)super.position(position);
    }

    /** the buffer that this is a reference to */
    public native AVFilterBuffer buf(); public native AVFilterBufferRef buf(AVFilterBuffer buf);
    /** picture/audio data for each plane */
    public native @Cast("uint8_t*") BytePointer data(int i); public native AVFilterBufferRef data(int i, BytePointer data);
    @MemberGetter public native @Cast("uint8_t**") PointerPointer data();
    /**
     * pointers to the data planes/channels.
     *
     * For video, this should simply point to data[].
     *
     * For planar audio, each channel has a separate data pointer, and
     * linesize[0] contains the size of each channel buffer.
     * For packed audio, there is just one data pointer, and linesize[0]
     * contains the total size of the buffer for all channels.
     *
     * Note: Both data and extended_data will always be set, but for planar
     * audio with more channels that can fit in data, extended_data must be used
     * in order to access all channels.
     */
    public native @Cast("uint8_t*") BytePointer extended_data(int i); public native AVFilterBufferRef extended_data(int i, BytePointer extended_data);
    @MemberGetter public native @Cast("uint8_t**") PointerPointer extended_data();
    /** number of bytes per line */
    public native int linesize(int i); public native AVFilterBufferRef linesize(int i, int linesize);
    @MemberGetter public native IntPointer linesize();

    /** video buffer specific properties */
    public native AVFilterBufferRefVideoProps video(); public native AVFilterBufferRef video(AVFilterBufferRefVideoProps video);
    /** audio buffer specific properties */
    public native AVFilterBufferRefAudioProps audio(); public native AVFilterBufferRef audio(AVFilterBufferRefAudioProps audio);

    /**
     * presentation timestamp. The time unit may change during
     * filtering, as it is specified in the link and the filter code
     * may need to rescale the PTS accordingly.
     */
    public native long pts(); public native AVFilterBufferRef pts(long pts);
    /** byte position in stream, -1 if unknown */
    public native long pos(); public native AVFilterBufferRef pos(long pos);

    /** media format */
    public native int format(); public native AVFilterBufferRef format(int format);

    /** permissions, see the AV_PERM_* flags */
    public native int perms(); public native AVFilterBufferRef perms(int perms);

    /** media type of buffer data */
    public native @Cast("AVMediaType") int type(); public native AVFilterBufferRef type(int type);

    /** dictionary containing metadata key=value tags */
    public native AVDictionary metadata(); public native AVFilterBufferRef metadata(AVDictionary metadata);
}

/**
 * Copy properties of src to dst, without copying the actual data
 */
public static native @Deprecated void avfilter_copy_buffer_ref_props(AVFilterBufferRef dst, AVFilterBufferRef src);

/**
 * Add a new reference to a buffer.
 *
 * @param ref   an existing reference to the buffer
 * @param pmask a bitmask containing the allowable permissions in the new
 *              reference
 * @return      a new reference to the buffer with the same properties as the
 *              old, excluding any permissions denied by pmask
 */
public static native @Deprecated AVFilterBufferRef avfilter_ref_buffer(AVFilterBufferRef ref, int pmask);

/**
 * Remove a reference to a buffer. If this is the last reference to the
 * buffer, the buffer itself is also automatically freed.
 *
 * @param ref reference to the buffer, may be NULL
 *
 * @note it is recommended to use avfilter_unref_bufferp() instead of this
 * function
 */
public static native @Deprecated void avfilter_unref_buffer(AVFilterBufferRef ref);

/**
 * Remove a reference to a buffer and set the pointer to NULL.
 * If this is the last reference to the buffer, the buffer itself
 * is also automatically freed.
 *
 * @param ref pointer to the buffer reference
 */
public static native @Deprecated void avfilter_unref_bufferp(@Cast("AVFilterBufferRef**") PointerPointer ref);
public static native @Deprecated void avfilter_unref_bufferp(@ByPtrPtr AVFilterBufferRef ref);
// #endif

/**
 * Get the number of channels of a buffer reference.
 */
public static native @Deprecated int avfilter_ref_get_channels(AVFilterBufferRef ref);

// #if FF_API_AVFILTERPAD_PUBLIC
/**
 * A filter pad used for either input or output.
 *
 * See doc/filter_design.txt for details on how to implement the methods.
 *
 * @warning this struct might be removed from public API.
 * users should call avfilter_pad_get_name() and avfilter_pad_get_type()
 * to access the name and type fields; there should be no need to access
 * any other fields from outside of libavfilter.
 */
public static class AVFilterPad extends Pointer {
    static { Loader.load(); }
    public AVFilterPad() { allocate(); }
    public AVFilterPad(int size) { allocateArray(size); }
    public AVFilterPad(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterPad position(int position) {
        return (AVFilterPad)super.position(position);
    }

    /**
     * Pad name. The name is unique among inputs and among outputs, but an
     * input may have the same name as an output. This may be NULL if this
     * pad has no need to ever be referenced by name.
     */
    @MemberGetter public native @Cast("const char*") BytePointer name();

    /**
     * AVFilterPad type.
     */
    public native @Cast("AVMediaType") int type(); public native AVFilterPad type(int type);

    /**
     * Input pads:
     * Minimum required permissions on incoming buffers. Any buffer with
     * insufficient permissions will be automatically copied by the filter
     * system to a new buffer which provides the needed access permissions.
     *
     * Output pads:
     * Guaranteed permissions on outgoing buffers. Any buffer pushed on the
     * link must have at least these permissions; this fact is checked by
     * asserts. It can be used to optimize buffer allocation.
     */
    public native @Deprecated int min_perms(); public native AVFilterPad min_perms(int min_perms);

    /**
     * Input pads:
     * Permissions which are not accepted on incoming buffers. Any buffer
     * which has any of these permissions set will be automatically copied
     * by the filter system to a new buffer which does not have those
     * permissions. This can be used to easily disallow buffers with
     * AV_PERM_REUSE.
     *
     * Output pads:
     * Permissions which are automatically removed on outgoing buffers. It
     * can be used to optimize buffer allocation.
     */
    public native @Deprecated int rej_perms(); public native AVFilterPad rej_perms(int rej_perms);

    /**
     * @deprecated unused
     */
    public static class Start_frame_AVFilterLink_AVFilterBufferRef extends FunctionPointer {
        static { Loader.load(); }
        public    Start_frame_AVFilterLink_AVFilterBufferRef(Pointer p) { super(p); }
        protected Start_frame_AVFilterLink_AVFilterBufferRef() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link, AVFilterBufferRef picref);
    }
    public native Start_frame_AVFilterLink_AVFilterBufferRef start_frame(); public native AVFilterPad start_frame(Start_frame_AVFilterLink_AVFilterBufferRef start_frame);

    /**
     * Callback function to get a video buffer. If NULL, the filter system will
     * use ff_default_get_video_buffer().
     *
     * Input video pads only.
     */
    public static class Get_video_buffer_AVFilterLink_int_int extends FunctionPointer {
        static { Loader.load(); }
        public    Get_video_buffer_AVFilterLink_int_int(Pointer p) { super(p); }
        protected Get_video_buffer_AVFilterLink_int_int() { allocate(); }
        private native void allocate();
        public native AVFrame call(AVFilterLink link, int w, int h);
    }
    public native Get_video_buffer_AVFilterLink_int_int get_video_buffer(); public native AVFilterPad get_video_buffer(Get_video_buffer_AVFilterLink_int_int get_video_buffer);

    /**
     * Callback function to get an audio buffer. If NULL, the filter system will
     * use ff_default_get_audio_buffer().
     *
     * Input audio pads only.
     */
    public static class Get_audio_buffer_AVFilterLink_int extends FunctionPointer {
        static { Loader.load(); }
        public    Get_audio_buffer_AVFilterLink_int(Pointer p) { super(p); }
        protected Get_audio_buffer_AVFilterLink_int() { allocate(); }
        private native void allocate();
        public native AVFrame call(AVFilterLink link, int nb_samples);
    }
    public native Get_audio_buffer_AVFilterLink_int get_audio_buffer(); public native AVFilterPad get_audio_buffer(Get_audio_buffer_AVFilterLink_int get_audio_buffer);

    /**
     * @deprecated unused
     */
    public static class End_frame_AVFilterLink extends FunctionPointer {
        static { Loader.load(); }
        public    End_frame_AVFilterLink(Pointer p) { super(p); }
        protected End_frame_AVFilterLink() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link);
    }
    public native End_frame_AVFilterLink end_frame(); public native AVFilterPad end_frame(End_frame_AVFilterLink end_frame);

    /**
     * @deprecated unused
     */
    public static class Draw_slice_AVFilterLink_int_int_int extends FunctionPointer {
        static { Loader.load(); }
        public    Draw_slice_AVFilterLink_int_int_int(Pointer p) { super(p); }
        protected Draw_slice_AVFilterLink_int_int_int() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link, int y, int height, int slice_dir);
    }
    public native Draw_slice_AVFilterLink_int_int_int draw_slice(); public native AVFilterPad draw_slice(Draw_slice_AVFilterLink_int_int_int draw_slice);

    /**
     * Filtering callback. This is where a filter receives a frame with
     * audio/video data and should do its processing.
     *
     * Input pads only.
     *
     * @return >= 0 on success, a negative AVERROR on error. This function
     * must ensure that frame is properly unreferenced on error if it
     * hasn't been passed on to another filter.
     */
    public static class Filter_frame_AVFilterLink_AVFrame extends FunctionPointer {
        static { Loader.load(); }
        public    Filter_frame_AVFilterLink_AVFrame(Pointer p) { super(p); }
        protected Filter_frame_AVFilterLink_AVFrame() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link, AVFrame frame);
    }
    public native Filter_frame_AVFilterLink_AVFrame filter_frame(); public native AVFilterPad filter_frame(Filter_frame_AVFilterLink_AVFrame filter_frame);

    /**
     * Frame poll callback. This returns the number of immediately available
     * samples. It should return a positive value if the next request_frame()
     * is guaranteed to return one frame (with no delay).
     *
     * Defaults to just calling the source poll_frame() method.
     *
     * Output pads only.
     */
    public static class Poll_frame_AVFilterLink extends FunctionPointer {
        static { Loader.load(); }
        public    Poll_frame_AVFilterLink(Pointer p) { super(p); }
        protected Poll_frame_AVFilterLink() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link);
    }
    public native Poll_frame_AVFilterLink poll_frame(); public native AVFilterPad poll_frame(Poll_frame_AVFilterLink poll_frame);

    /**
     * Frame request callback. A call to this should result in at least one
     * frame being output over the given link. This should return zero on
     * success, and another value on error.
     * See ff_request_frame() for the error codes with a specific
     * meaning.
     *
     * Output pads only.
     */
    public static class Request_frame_AVFilterLink extends FunctionPointer {
        static { Loader.load(); }
        public    Request_frame_AVFilterLink(Pointer p) { super(p); }
        protected Request_frame_AVFilterLink() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link);
    }
    public native Request_frame_AVFilterLink request_frame(); public native AVFilterPad request_frame(Request_frame_AVFilterLink request_frame);

    /**
     * Link configuration callback.
     *
     * For output pads, this should set the following link properties:
     * video: width, height, sample_aspect_ratio, time_base
     * audio: sample_rate.
     *
     * This should NOT set properties such as format, channel_layout, etc which
     * are negotiated between filters by the filter system using the
     * query_formats() callback before this function is called.
     *
     * For input pads, this should check the properties of the link, and update
     * the filter's internal state as necessary.
     *
     * For both input and output pads, this should return zero on success,
     * and another value on error.
     */
    public static class Config_props_AVFilterLink extends FunctionPointer {
        static { Loader.load(); }
        public    Config_props_AVFilterLink(Pointer p) { super(p); }
        protected Config_props_AVFilterLink() { allocate(); }
        private native void allocate();
        public native int call(AVFilterLink link);
    }
    public native Config_props_AVFilterLink config_props(); public native AVFilterPad config_props(Config_props_AVFilterLink config_props);

    /**
     * The filter expects a fifo to be inserted on its input link,
     * typically because it has a delay.
     *
     * input pads only.
     */
    public native int needs_fifo(); public native AVFilterPad needs_fifo(int needs_fifo);

    /**
     * The filter expects writable frames from its input link,
     * duplicating data buffers if needed.
     *
     * input pads only.
     */
    public native int needs_writable(); public native AVFilterPad needs_writable(int needs_writable);
}
// #endif

/**
 * Get the number of elements in a NULL-terminated array of AVFilterPads (e.g.
 * AVFilter.inputs/outputs).
 */
public static native int avfilter_pad_count(@Const AVFilterPad pads);

/**
 * Get the name of an AVFilterPad.
 *
 * @param pads an array of AVFilterPads
 * @param pad_idx index of the pad in the array it; is the caller's
 *                responsibility to ensure the index is valid
 *
 * @return name of the pad_idx'th pad in pads
 */
public static native @Cast("const char*") BytePointer avfilter_pad_get_name(@Const AVFilterPad pads, int pad_idx);

/**
 * Get the type of an AVFilterPad.
 *
 * @param pads an array of AVFilterPads
 * @param pad_idx index of the pad in the array; it is the caller's
 *                responsibility to ensure the index is valid
 *
 * @return type of the pad_idx'th pad in pads
 */
public static native @Cast("AVMediaType") int avfilter_pad_get_type(@Const AVFilterPad pads, int pad_idx);

/**
 * The number of the filter inputs is not determined just by AVFilter.inputs.
 * The filter might add additional inputs during initialization depending on the
 * options supplied to it.
 */
public static final int AVFILTER_FLAG_DYNAMIC_INPUTS =        (1 << 0);
/**
 * The number of the filter outputs is not determined just by AVFilter.outputs.
 * The filter might add additional outputs during initialization depending on
 * the options supplied to it.
 */
public static final int AVFILTER_FLAG_DYNAMIC_OUTPUTS =       (1 << 1);
/**
 * The filter supports multithreading by splitting frames into multiple parts
 * and processing them concurrently.
 */
public static final int AVFILTER_FLAG_SLICE_THREADS =         (1 << 2);
/**
 * Some filters support a generic "enable" expression option that can be used
 * to enable or disable a filter in the timeline. Filters supporting this
 * option have this flag set. When the enable expression is false, the default
 * no-op filter_frame() function is called in place of the filter_frame()
 * callback defined on each input pad, thus the frame is passed unchanged to
 * the next filters.
 */
public static final int AVFILTER_FLAG_SUPPORT_TIMELINE_GENERIC =  (1 << 16);
/**
 * Same as AVFILTER_FLAG_SUPPORT_TIMELINE_GENERIC, except that the filter will
 * have its filter_frame() callback(s) called as usual even when the enable
 * expression is false. The filter will disable filtering within the
 * filter_frame() callback(s) itself, for example executing code depending on
 * the AVFilterContext->is_disabled value.
 */
public static final int AVFILTER_FLAG_SUPPORT_TIMELINE_INTERNAL = (1 << 17);
/**
 * Handy mask to test whether the filter supports or no the timeline feature
 * (internally or generically).
 */
public static final int AVFILTER_FLAG_SUPPORT_TIMELINE = (AVFILTER_FLAG_SUPPORT_TIMELINE_GENERIC | AVFILTER_FLAG_SUPPORT_TIMELINE_INTERNAL);

/**
 * Filter definition. This defines the pads a filter contains, and all the
 * callback functions used to interact with the filter.
 */
public static class AVFilter extends Pointer {
    static { Loader.load(); }
    public AVFilter() { allocate(); }
    public AVFilter(int size) { allocateArray(size); }
    public AVFilter(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilter position(int position) {
        return (AVFilter)super.position(position);
    }

    /**
     * Filter name. Must be non-NULL and unique among filters.
     */
    @MemberGetter public native @Cast("const char*") BytePointer name();

    /**
     * A description of the filter. May be NULL.
     *
     * You should use the NULL_IF_CONFIG_SMALL() macro to define it.
     */
    @MemberGetter public native @Cast("const char*") BytePointer description();

    /**
     * List of inputs, terminated by a zeroed element.
     *
     * NULL if there are no (static) inputs. Instances of filters with
     * AVFILTER_FLAG_DYNAMIC_INPUTS set may have more inputs than present in
     * this list.
     */
    @MemberGetter public native @Const AVFilterPad inputs();
    /**
     * List of outputs, terminated by a zeroed element.
     *
     * NULL if there are no (static) outputs. Instances of filters with
     * AVFILTER_FLAG_DYNAMIC_OUTPUTS set may have more outputs than present in
     * this list.
     */
    @MemberGetter public native @Const AVFilterPad outputs();

    /**
     * A class for the private data, used to declare filter private AVOptions.
     * This field is NULL for filters that do not declare any options.
     *
     * If this field is non-NULL, the first member of the filter private data
     * must be a pointer to AVClass, which will be set by libavfilter generic
     * code to this class.
     */
    @MemberGetter public native @Const AVClass priv_class();

    /**
     * A combination of AVFILTER_FLAG_*
     */
    public native int flags(); public native AVFilter flags(int flags);

    /*****************************************************************
     * All fields below this line are not part of the public API. They
     * may not be used outside of libavfilter and can be changed and
     * removed at will.
     * New public fields should be added right above.
     *****************************************************************
     */

    /**
     * Filter initialization function.
     *
     * This callback will be called only once during the filter lifetime, after
     * all the options have been set, but before links between filters are
     * established and format negotiation is done.
     *
     * Basic filter initialization should be done here. Filters with dynamic
     * inputs and/or outputs should create those inputs/outputs here based on
     * provided options. No more changes to this filter's inputs/outputs can be
     * done after this callback.
     *
     * This callback must not assume that the filter links exist or frame
     * parameters are known.
     *
     * @ref AVFilter.uninit "uninit" is guaranteed to be called even if
     * initialization fails, so this callback does not have to clean up on
     * failure.
     *
     * @return 0 on success, a negative AVERROR on failure
     */
    public static class Init_AVFilterContext extends FunctionPointer {
        static { Loader.load(); }
        public    Init_AVFilterContext(Pointer p) { super(p); }
        protected Init_AVFilterContext() { allocate(); }
        private native void allocate();
        public native int call(AVFilterContext ctx);
    }
    public native Init_AVFilterContext init(); public native AVFilter init(Init_AVFilterContext init);

    /**
     * Should be set instead of @ref AVFilter.init "init" by the filters that
     * want to pass a dictionary of AVOptions to nested contexts that are
     * allocated during init.
     *
     * On return, the options dict should be freed and replaced with one that
     * contains all the options which could not be processed by this filter (or
     * with NULL if all the options were processed).
     *
     * Otherwise the semantics is the same as for @ref AVFilter.init "init".
     */
    public static class Init_dict_AVFilterContext_PointerPointer extends FunctionPointer {
        static { Loader.load(); }
        public    Init_dict_AVFilterContext_PointerPointer(Pointer p) { super(p); }
        protected Init_dict_AVFilterContext_PointerPointer() { allocate(); }
        private native void allocate();
        public native int call(AVFilterContext ctx, @Cast("AVDictionary**") PointerPointer options);
    }
    public native Init_dict_AVFilterContext_PointerPointer init_dict(); public native AVFilter init_dict(Init_dict_AVFilterContext_PointerPointer init_dict);

    /**
     * Filter uninitialization function.
     *
     * Called only once right before the filter is freed. Should deallocate any
     * memory held by the filter, release any buffer references, etc. It does
     * not need to deallocate the AVFilterContext.priv memory itself.
     *
     * This callback may be called even if @ref AVFilter.init "init" was not
     * called or failed, so it must be prepared to handle such a situation.
     */
    public static class Uninit_AVFilterContext extends FunctionPointer {
        static { Loader.load(); }
        public    Uninit_AVFilterContext(Pointer p) { super(p); }
        protected Uninit_AVFilterContext() { allocate(); }
        private native void allocate();
        public native void call(AVFilterContext ctx);
    }
    public native Uninit_AVFilterContext uninit(); public native AVFilter uninit(Uninit_AVFilterContext uninit);

    /**
     * Query formats supported by the filter on its inputs and outputs.
     *
     * This callback is called after the filter is initialized (so the inputs
     * and outputs are fixed), shortly before the format negotiation. This
     * callback may be called more than once.
     *
     * This callback must set AVFilterLink.out_formats on every input link and
     * AVFilterLink.in_formats on every output link to a list of pixel/sample
     * formats that the filter supports on that link. For audio links, this
     * filter must also set @ref AVFilterLink.in_samplerates "in_samplerates" /
     * @ref AVFilterLink.out_samplerates "out_samplerates" and
     * @ref AVFilterLink.in_channel_layouts "in_channel_layouts" /
     * @ref AVFilterLink.out_channel_layouts "out_channel_layouts" analogously.
     *
     * This callback may be NULL for filters with one input, in which case
     * libavfilter assumes that it supports all input formats and preserves
     * them on output.
     *
     * @return zero on success, a negative value corresponding to an
     * AVERROR code otherwise
     */
    public static class Query_formats_AVFilterContext extends FunctionPointer {
        static { Loader.load(); }
        public    Query_formats_AVFilterContext(Pointer p) { super(p); }
        protected Query_formats_AVFilterContext() { allocate(); }
        private native void allocate();
        public native int call(AVFilterContext arg0);
    }
    public native Query_formats_AVFilterContext query_formats(); public native AVFilter query_formats(Query_formats_AVFilterContext query_formats);

    /** size of private data to allocate for the filter */
    public native int priv_size(); public native AVFilter priv_size(int priv_size);

    /**
     * Used by the filter registration system. Must not be touched by any other
     * code.
     */
    public native AVFilter next(); public native AVFilter next(AVFilter next);

    /**
     * Make the filter instance process a command.
     *
     * @param cmd    the command to process, for handling simplicity all commands must be alphanumeric only
     * @param arg    the argument for the command
     * @param res    a buffer with size res_size where the filter(s) can return a response. This must not change when the command is not supported.
     * @param flags  if AVFILTER_CMD_FLAG_FAST is set and the command would be
     *               time consuming then a filter should treat it like an unsupported command
     *
     * @returns >=0 on success otherwise an error code.
     *          AVERROR(ENOSYS) on unsupported commands
     */
    public static class Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int extends FunctionPointer {
        static { Loader.load(); }
        public    Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int(Pointer p) { super(p); }
        protected Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int() { allocate(); }
        private native void allocate();
        public native int call(AVFilterContext arg0, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") BytePointer res, int res_len, int flags);
    }
    public native Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int process_command(); public native AVFilter process_command(Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int process_command);

    /**
     * Filter initialization function, alternative to the init()
     * callback. Args contains the user-supplied parameters, opaque is
     * used for providing binary data.
     */
    public static class Init_opaque_AVFilterContext_Pointer extends FunctionPointer {
        static { Loader.load(); }
        public    Init_opaque_AVFilterContext_Pointer(Pointer p) { super(p); }
        protected Init_opaque_AVFilterContext_Pointer() { allocate(); }
        private native void allocate();
        public native int call(AVFilterContext ctx, Pointer opaque);
    }
    public native Init_opaque_AVFilterContext_Pointer init_opaque(); public native AVFilter init_opaque(Init_opaque_AVFilterContext_Pointer init_opaque);
}

/**
 * Process multiple parts of the frame concurrently.
 */
public static final int AVFILTER_THREAD_SLICE = (1 << 0);

@Opaque public static class AVFilterInternal extends Pointer {
    public AVFilterInternal() { }
    public AVFilterInternal(Pointer p) { super(p); }
}

/** An instance of a filter */
public static class AVFilterContext extends Pointer {
    static { Loader.load(); }
    public AVFilterContext() { allocate(); }
    public AVFilterContext(int size) { allocateArray(size); }
    public AVFilterContext(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterContext position(int position) {
        return (AVFilterContext)super.position(position);
    }

    /** needed for av_log() and filters common options */
    @MemberGetter public native @Const AVClass av_class();

    /** the AVFilter of which this is an instance */
    @MemberGetter public native @Const AVFilter filter();

    /** name of this filter instance */
    public native @Cast("char*") BytePointer name(); public native AVFilterContext name(BytePointer name);

    /** array of input pads */
    public native AVFilterPad input_pads(); public native AVFilterContext input_pads(AVFilterPad input_pads);
    /** array of pointers to input links */
    public native AVFilterLink inputs(int i); public native AVFilterContext inputs(int i, AVFilterLink inputs);
    @MemberGetter public native @Cast("AVFilterLink**") PointerPointer inputs();
// #if FF_API_FOO_COUNT
    /** @deprecated use nb_inputs */
    public native @Cast("unsigned") @Deprecated int input_count(); public native AVFilterContext input_count(int input_count);
// #endif
    /** number of input pads */
    public native @Cast("unsigned") int nb_inputs(); public native AVFilterContext nb_inputs(int nb_inputs);

    /** array of output pads */
    public native AVFilterPad output_pads(); public native AVFilterContext output_pads(AVFilterPad output_pads);
    /** array of pointers to output links */
    public native AVFilterLink outputs(int i); public native AVFilterContext outputs(int i, AVFilterLink outputs);
    @MemberGetter public native @Cast("AVFilterLink**") PointerPointer outputs();
// #if FF_API_FOO_COUNT
    /** @deprecated use nb_outputs */
    public native @Cast("unsigned") @Deprecated int output_count(); public native AVFilterContext output_count(int output_count);
// #endif
    /** number of output pads */
    public native @Cast("unsigned") int nb_outputs(); public native AVFilterContext nb_outputs(int nb_outputs);

    /** private data for use by the filter */
    public native Pointer priv(); public native AVFilterContext priv(Pointer priv);

    /** filtergraph this filter belongs to */
    public native AVFilterGraph graph(); public native AVFilterContext graph(AVFilterGraph graph);

    /**
     * Type of multithreading being allowed/used. A combination of
     * AVFILTER_THREAD_* flags.
     *
     * May be set by the caller before initializing the filter to forbid some
     * or all kinds of multithreading for this filter. The default is allowing
     * everything.
     *
     * When the filter is initialized, this field is combined using bit AND with
     * AVFilterGraph.thread_type to get the final mask used for determining
     * allowed threading types. I.e. a threading type needs to be set in both
     * to be allowed.
     *
     * After the filter is initialzed, libavfilter sets this field to the
     * threading type that is actually used (0 for no multithreading).
     */
    public native int thread_type(); public native AVFilterContext thread_type(int thread_type);

    /**
     * An opaque struct for libavfilter internal use.
     */
    public native AVFilterInternal internal(); public native AVFilterContext internal(AVFilterInternal internal);

    public native @Cast("AVFilterCommand*") Pointer command_queue(); public native AVFilterContext command_queue(Pointer command_queue);

    /** enable expression string */
    public native @Cast("char*") BytePointer enable_str(); public native AVFilterContext enable_str(BytePointer enable_str);
    /** parsed expression (AVExpr*) */
    public native Pointer enable(); public native AVFilterContext enable(Pointer enable);
    /** variable values for the enable expression */
    public native DoublePointer var_values(); public native AVFilterContext var_values(DoublePointer var_values);
    /** the enabled state from the last expression evaluation */
    public native int is_disabled(); public native AVFilterContext is_disabled(int is_disabled);
}

/**
 * A link between two filters. This contains pointers to the source and
 * destination filters between which this link exists, and the indexes of
 * the pads involved. In addition, this link also contains the parameters
 * which have been negotiated and agreed upon between the filter, such as
 * image dimensions, format, etc.
 */
public static class AVFilterLink extends Pointer {
    static { Loader.load(); }
    public AVFilterLink() { allocate(); }
    public AVFilterLink(int size) { allocateArray(size); }
    public AVFilterLink(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterLink position(int position) {
        return (AVFilterLink)super.position(position);
    }

    /** source filter */
    public native AVFilterContext src(); public native AVFilterLink src(AVFilterContext src);
    /** output pad on the source filter */
    public native AVFilterPad srcpad(); public native AVFilterLink srcpad(AVFilterPad srcpad);

    /** dest filter */
    public native AVFilterContext dst(); public native AVFilterLink dst(AVFilterContext dst);
    /** input pad on the dest filter */
    public native AVFilterPad dstpad(); public native AVFilterLink dstpad(AVFilterPad dstpad);

    /** filter media type */
    public native @Cast("AVMediaType") int type(); public native AVFilterLink type(int type);

    /* These parameters apply only to video */
    /** agreed upon image width */
    public native int w(); public native AVFilterLink w(int w);
    /** agreed upon image height */
    public native int h(); public native AVFilterLink h(int h);
    /** agreed upon sample aspect ratio */
    public native @ByRef AVRational sample_aspect_ratio(); public native AVFilterLink sample_aspect_ratio(AVRational sample_aspect_ratio);
    /* These parameters apply only to audio */
    /** channel layout of current buffer (see libavutil/channel_layout.h) */
    public native @Cast("uint64_t") long channel_layout(); public native AVFilterLink channel_layout(long channel_layout);
    /** samples per second */
    public native int sample_rate(); public native AVFilterLink sample_rate(int sample_rate);

    /** agreed upon media format */
    public native int format(); public native AVFilterLink format(int format);

    /**
     * Define the time base used by the PTS of the frames/samples
     * which will pass through this link.
     * During the configuration stage, each filter is supposed to
     * change only the output timebase, while the timebase of the
     * input link is assumed to be an unchangeable property.
     */
    public native @ByRef AVRational time_base(); public native AVFilterLink time_base(AVRational time_base);

    /*****************************************************************
     * All fields below this line are not part of the public API. They
     * may not be used outside of libavfilter and can be changed and
     * removed at will.
     * New public fields should be added right above.
     *****************************************************************
     */
    /**
     * Lists of formats and channel layouts supported by the input and output
     * filters respectively. These lists are used for negotiating the format
     * to actually be used, which will be loaded into the format and
     * channel_layout members, above, when chosen.
     *
     */
    public native AVFilterFormats in_formats(); public native AVFilterLink in_formats(AVFilterFormats in_formats);
    public native AVFilterFormats out_formats(); public native AVFilterLink out_formats(AVFilterFormats out_formats);

    /**
     * Lists of channel layouts and sample rates used for automatic
     * negotiation.
     */
    public native AVFilterFormats in_samplerates(); public native AVFilterLink in_samplerates(AVFilterFormats in_samplerates);
    public native AVFilterFormats out_samplerates(); public native AVFilterLink out_samplerates(AVFilterFormats out_samplerates);
    public native @Cast("AVFilterChannelLayouts*") Pointer in_channel_layouts(); public native AVFilterLink in_channel_layouts(Pointer in_channel_layouts);
    public native @Cast("AVFilterChannelLayouts*") Pointer out_channel_layouts(); public native AVFilterLink out_channel_layouts(Pointer out_channel_layouts);

    /**
     * Audio only, the destination filter sets this to a non-zero value to
     * request that buffers with the given number of samples should be sent to
     * it. AVFilterPad.needs_fifo must also be set on the corresponding input
     * pad.
     * Last buffer before EOF will be padded with silence.
     */
    public native int request_samples(); public native AVFilterLink request_samples(int request_samples);

    /** stage of the initialization of the link properties (dimensions, etc) */
    /** enum AVFilterLink::init_state */
    public static final int
        /** not started */
        AVLINK_UNINIT = 0,
        /** started, but incomplete */
        AVLINK_STARTINIT = 1,
        /** complete */
        AVLINK_INIT = 2;

    public native @Cast("AVFilterPool*") Pointer pool(); public native AVFilterLink pool(Pointer pool);

    /**
     * Graph the filter belongs to.
     */
    public native AVFilterGraph graph(); public native AVFilterLink graph(AVFilterGraph graph);

    /**
     * Current timestamp of the link, as defined by the most recent
     * frame(s), in AV_TIME_BASE units.
     */
    public native long current_pts(); public native AVFilterLink current_pts(long current_pts);

    /**
     * Index in the age array.
     */
    public native int age_index(); public native AVFilterLink age_index(int age_index);

    /**
     * Frame rate of the stream on the link, or 1/0 if unknown;
     * if left to 0/0, will be automatically be copied from the first input
     * of the source filter if it exists.
     *
     * Sources should set it to the best estimation of the real frame rate.
     * Filters should update it if necessary depending on their function.
     * Sinks can use it to set a default output frame rate.
     * It is similar to the r_frame_rate field in AVStream.
     */
    public native @ByRef AVRational frame_rate(); public native AVFilterLink frame_rate(AVRational frame_rate);

    /**
     * Buffer partially filled with samples to achieve a fixed/minimum size.
     */
    public native AVFrame partial_buf(); public native AVFilterLink partial_buf(AVFrame partial_buf);

    /**
     * Size of the partial buffer to allocate.
     * Must be between min_samples and max_samples.
     */
    public native int partial_buf_size(); public native AVFilterLink partial_buf_size(int partial_buf_size);

    /**
     * Minimum number of samples to filter at once. If filter_frame() is
     * called with fewer samples, it will accumulate them in partial_buf.
     * This field and the related ones must not be changed after filtering
     * has started.
     * If 0, all related fields are ignored.
     */
    public native int min_samples(); public native AVFilterLink min_samples(int min_samples);

    /**
     * Maximum number of samples to filter at once. If filter_frame() is
     * called with more samples, it will split them.
     */
    public native int max_samples(); public native AVFilterLink max_samples(int max_samples);

    /**
     * The buffer reference currently being received across the link by the
     * destination filter. This is used internally by the filter system to
     * allow automatic copying of buffers which do not have sufficient
     * permissions for the destination. This should not be accessed directly
     * by the filters.
     */
    public native AVFilterBufferRef cur_buf_copy(); public native AVFilterLink cur_buf_copy(AVFilterBufferRef cur_buf_copy);

    /**
     * True if the link is closed.
     * If set, all attemps of start_frame, filter_frame or request_frame
     * will fail with AVERROR_EOF, and if necessary the reference will be
     * destroyed.
     * If request_frame returns AVERROR_EOF, this flag is set on the
     * corresponding link.
     * It can be set also be set by either the source or the destination
     * filter.
     */
    public native int closed(); public native AVFilterLink closed(int closed);

    /**
     * Number of channels.
     */
    public native int channels(); public native AVFilterLink channels(int channels);

    /**
     * True if a frame is being requested on the link.
     * Used internally by the framework.
     */
    public native @Cast("unsigned") int frame_requested(); public native AVFilterLink frame_requested(int frame_requested);

    /**
     * Link processing flags.
     */
    public native @Cast("unsigned") int flags(); public native AVFilterLink flags(int flags);

    /**
     * Number of past frames sent through the link.
     */
    public native long frame_count(); public native AVFilterLink frame_count(long frame_count);
}

/**
 * Link two filters together.
 *
 * @param src    the source filter
 * @param srcpad index of the output pad on the source filter
 * @param dst    the destination filter
 * @param dstpad index of the input pad on the destination filter
 * @return       zero on success
 */
public static native int avfilter_link(AVFilterContext src, @Cast("unsigned") int srcpad,
                  AVFilterContext dst, @Cast("unsigned") int dstpad);

/**
 * Free the link in *link, and set its pointer to NULL.
 */
public static native void avfilter_link_free(@Cast("AVFilterLink**") PointerPointer link);
public static native void avfilter_link_free(@ByPtrPtr AVFilterLink link);

/**
 * Get the number of channels of a link.
 */
public static native int avfilter_link_get_channels(AVFilterLink link);

/**
 * Set the closed field of a link.
 */
public static native void avfilter_link_set_closed(AVFilterLink link, int closed);

/**
 * Negotiate the media format, dimensions, etc of all inputs to a filter.
 *
 * @param filter the filter to negotiate the properties for its inputs
 * @return       zero on successful negotiation
 */
public static native int avfilter_config_links(AVFilterContext filter);

// #if FF_API_AVFILTERBUFFER
/**
 * Create a buffer reference wrapped around an already allocated image
 * buffer.
 *
 * @param data pointers to the planes of the image to reference
 * @param linesize linesizes for the planes of the image to reference
 * @param perms the required access permissions
 * @param w the width of the image specified by the data and linesize arrays
 * @param h the height of the image specified by the data and linesize arrays
 * @param format the pixel format of the image specified by the data and linesize arrays
 */
public static native @Deprecated AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@Cast("uint8_t*const*") PointerPointer data, @Const IntPointer linesize, int perms,
                                          int w, int h, @Cast("AVPixelFormat") int format);
public static native @Deprecated AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@Cast("uint8_t*const*") @ByPtrPtr BytePointer data, @Const IntPointer linesize, int perms,
                                          int w, int h, @Cast("AVPixelFormat") int format);
public static native @Deprecated AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@Cast("uint8_t*const*") @ByPtrPtr ByteBuffer data, @Const IntBuffer linesize, int perms,
                                          int w, int h, @Cast("AVPixelFormat") int format);
public static native @Deprecated AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@Cast("uint8_t*const*") @ByPtrPtr byte[] data, @Const int[] linesize, int perms,
                                          int w, int h, @Cast("AVPixelFormat") int format);

/**
 * Create an audio buffer reference wrapped around an already
 * allocated samples buffer.
 *
 * See avfilter_get_audio_buffer_ref_from_arrays_channels() for a version
 * that can handle unknown channel layouts.
 *
 * @param data           pointers to the samples plane buffers
 * @param linesize       linesize for the samples plane buffers
 * @param perms          the required access permissions
 * @param nb_samples     number of samples per channel
 * @param sample_fmt     the format of each sample in the buffer to allocate
 * @param channel_layout the channel layout of the buffer
 */
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@Cast("uint8_t**") PointerPointer data,
                                                             int linesize,
                                                             int perms,
                                                             int nb_samples,
                                                             @Cast("AVSampleFormat") int sample_fmt,
                                                             @Cast("uint64_t") long channel_layout);
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@Cast("uint8_t**") @ByPtrPtr BytePointer data,
                                                             int linesize,
                                                             int perms,
                                                             int nb_samples,
                                                             @Cast("AVSampleFormat") int sample_fmt,
                                                             @Cast("uint64_t") long channel_layout);
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@Cast("uint8_t**") @ByPtrPtr ByteBuffer data,
                                                             int linesize,
                                                             int perms,
                                                             int nb_samples,
                                                             @Cast("AVSampleFormat") int sample_fmt,
                                                             @Cast("uint64_t") long channel_layout);
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@Cast("uint8_t**") @ByPtrPtr byte[] data,
                                                             int linesize,
                                                             int perms,
                                                             int nb_samples,
                                                             @Cast("AVSampleFormat") int sample_fmt,
                                                             @Cast("uint64_t") long channel_layout);
/**
 * Create an audio buffer reference wrapped around an already
 * allocated samples buffer.
 *
 * @param data           pointers to the samples plane buffers
 * @param linesize       linesize for the samples plane buffers
 * @param perms          the required access permissions
 * @param nb_samples     number of samples per channel
 * @param sample_fmt     the format of each sample in the buffer to allocate
 * @param channels       the number of channels of the buffer
 * @param channel_layout the channel layout of the buffer,
 *                       must be either 0 or consistent with channels
 */
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@Cast("uint8_t**") PointerPointer data,
                                                                      int linesize,
                                                                      int perms,
                                                                      int nb_samples,
                                                                      @Cast("AVSampleFormat") int sample_fmt,
                                                                      int channels,
                                                                      @Cast("uint64_t") long channel_layout);
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@Cast("uint8_t**") @ByPtrPtr BytePointer data,
                                                                      int linesize,
                                                                      int perms,
                                                                      int nb_samples,
                                                                      @Cast("AVSampleFormat") int sample_fmt,
                                                                      int channels,
                                                                      @Cast("uint64_t") long channel_layout);
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@Cast("uint8_t**") @ByPtrPtr ByteBuffer data,
                                                                      int linesize,
                                                                      int perms,
                                                                      int nb_samples,
                                                                      @Cast("AVSampleFormat") int sample_fmt,
                                                                      int channels,
                                                                      @Cast("uint64_t") long channel_layout);
public static native @Deprecated AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@Cast("uint8_t**") @ByPtrPtr byte[] data,
                                                                      int linesize,
                                                                      int perms,
                                                                      int nb_samples,
                                                                      @Cast("AVSampleFormat") int sample_fmt,
                                                                      int channels,
                                                                      @Cast("uint64_t") long channel_layout);

// #endif


/** Stop once a filter understood the command (for target=all for example), fast filters are favored automatically */
public static final int AVFILTER_CMD_FLAG_ONE =   1;
/** Only execute command when its fast (like a video out that supports contrast adjustment in hw) */
public static final int AVFILTER_CMD_FLAG_FAST =  2;

/**
 * Make the filter instance process a command.
 * It is recommended to use avfilter_graph_send_command().
 */
public static native int avfilter_process_command(AVFilterContext filter, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") BytePointer res, int res_len, int flags);
public static native int avfilter_process_command(AVFilterContext filter, String cmd, String arg, @Cast("char*") ByteBuffer res, int res_len, int flags);
public static native int avfilter_process_command(AVFilterContext filter, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") byte[] res, int res_len, int flags);
public static native int avfilter_process_command(AVFilterContext filter, String cmd, String arg, @Cast("char*") BytePointer res, int res_len, int flags);
public static native int avfilter_process_command(AVFilterContext filter, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") ByteBuffer res, int res_len, int flags);
public static native int avfilter_process_command(AVFilterContext filter, String cmd, String arg, @Cast("char*") byte[] res, int res_len, int flags);

/** Initialize the filter system. Register all builtin filters. */
public static native void avfilter_register_all();

// #if FF_API_OLD_FILTER_REGISTER
/** Uninitialize the filter system. Unregister all filters. */
public static native @Deprecated void avfilter_uninit();
// #endif

/**
 * Register a filter. This is only needed if you plan to use
 * avfilter_get_by_name later to lookup the AVFilter structure by name. A
 * filter can still by instantiated with avfilter_graph_alloc_filter even if it
 * is not registered.
 *
 * @param filter the filter to register
 * @return 0 if the registration was successful, a negative value
 * otherwise
 */
public static native int avfilter_register(AVFilter filter);

/**
 * Get a filter definition matching the given name.
 *
 * @param name the filter name to find
 * @return     the filter definition, if any matching one is registered.
 *             NULL if none found.
 */
// #if !FF_API_NOCONST_GET_NAME
// #endif
public static native AVFilter avfilter_get_by_name(@Cast("const char*") BytePointer name);
public static native AVFilter avfilter_get_by_name(String name);

/**
 * Iterate over all registered filters.
 * @return If prev is non-NULL, next registered filter after prev or NULL if
 * prev is the last filter. If prev is NULL, return the first registered filter.
 */
public static native @Const AVFilter avfilter_next(@Const AVFilter prev);

// #if FF_API_OLD_FILTER_REGISTER
/**
 * If filter is NULL, returns a pointer to the first registered filter pointer,
 * if filter is non-NULL, returns the next pointer after filter.
 * If the returned pointer points to NULL, the last registered filter
 * was already reached.
 * @deprecated use avfilter_next()
 */
public static native @Cast("AVFilter**") @Deprecated PointerPointer av_filter_next(@Cast("AVFilter**") PointerPointer filter);
public static native @Deprecated @ByPtrPtr AVFilter av_filter_next(@ByPtrPtr AVFilter filter);
// #endif

// #if FF_API_AVFILTER_OPEN
/**
 * Create a filter instance.
 *
 * @param filter_ctx put here a pointer to the created filter context
 * on success, NULL on failure
 * @param filter    the filter to create an instance of
 * @param inst_name Name to give to the new instance. Can be NULL for none.
 * @return >= 0 in case of success, a negative error code otherwise
 * @deprecated use avfilter_graph_alloc_filter() instead
 */
public static native @Deprecated int avfilter_open(@Cast("AVFilterContext**") PointerPointer filter_ctx, AVFilter filter, @Cast("const char*") BytePointer inst_name);
public static native @Deprecated int avfilter_open(@ByPtrPtr AVFilterContext filter_ctx, AVFilter filter, @Cast("const char*") BytePointer inst_name);
public static native @Deprecated int avfilter_open(@ByPtrPtr AVFilterContext filter_ctx, AVFilter filter, String inst_name);
// #endif


// #if FF_API_AVFILTER_INIT_FILTER
/**
 * Initialize a filter.
 *
 * @param filter the filter to initialize
 * @param args   A string of parameters to use when initializing the filter.
 *               The format and meaning of this string varies by filter.
 * @param opaque Any extra non-string data needed by the filter. The meaning
 *               of this parameter varies by filter.
 * @return       zero on success
 */
public static native @Deprecated int avfilter_init_filter(AVFilterContext filter, @Cast("const char*") BytePointer args, Pointer opaque);
public static native @Deprecated int avfilter_init_filter(AVFilterContext filter, String args, Pointer opaque);
// #endif

/**
 * Initialize a filter with the supplied parameters.
 *
 * @param ctx  uninitialized filter context to initialize
 * @param args Options to initialize the filter with. This must be a
 *             ':'-separated list of options in the 'key=value' form.
 *             May be NULL if the options have been set directly using the
 *             AVOptions API or there are no options that need to be set.
 * @return 0 on success, a negative AVERROR on failure
 */
public static native int avfilter_init_str(AVFilterContext ctx, @Cast("const char*") BytePointer args);
public static native int avfilter_init_str(AVFilterContext ctx, String args);

/**
 * Initialize a filter with the supplied dictionary of options.
 *
 * @param ctx     uninitialized filter context to initialize
 * @param options An AVDictionary filled with options for this filter. On
 *                return this parameter will be destroyed and replaced with
 *                a dict containing options that were not found. This dictionary
 *                must be freed by the caller.
 *                May be NULL, then this function is equivalent to
 *                avfilter_init_str() with the second parameter set to NULL.
 * @return 0 on success, a negative AVERROR on failure
 *
 * @note This function and avfilter_init_str() do essentially the same thing,
 * the difference is in manner in which the options are passed. It is up to the
 * calling code to choose whichever is more preferable. The two functions also
 * behave differently when some of the provided options are not declared as
 * supported by the filter. In such a case, avfilter_init_str() will fail, but
 * this function will leave those extra options in the options AVDictionary and
 * continue as usual.
 */
public static native int avfilter_init_dict(AVFilterContext ctx, @Cast("AVDictionary**") PointerPointer options);
public static native int avfilter_init_dict(AVFilterContext ctx, @ByPtrPtr AVDictionary options);

/**
 * Free a filter context. This will also remove the filter from its
 * filtergraph's list of filters.
 *
 * @param filter the filter to free
 */
public static native void avfilter_free(AVFilterContext filter);

/**
 * Insert a filter in the middle of an existing link.
 *
 * @param link the link into which the filter should be inserted
 * @param filt the filter to be inserted
 * @param filt_srcpad_idx the input pad on the filter to connect
 * @param filt_dstpad_idx the output pad on the filter to connect
 * @return     zero on success
 */
public static native int avfilter_insert_filter(AVFilterLink link, AVFilterContext filt,
                           @Cast("unsigned") int filt_srcpad_idx, @Cast("unsigned") int filt_dstpad_idx);

// #if FF_API_AVFILTERBUFFER
/**
 * Copy the frame properties of src to dst, without copying the actual
 * image data.
 *
 * @return 0 on success, a negative number on error.
 */
public static native @Deprecated int avfilter_copy_frame_props(AVFilterBufferRef dst, @Const AVFrame src);

/**
 * Copy the frame properties and data pointers of src to dst, without copying
 * the actual data.
 *
 * @return 0 on success, a negative number on error.
 */
public static native @Deprecated int avfilter_copy_buf_props(AVFrame dst, @Const AVFilterBufferRef src);
// #endif

/**
 * @return AVClass for AVFilterContext.
 *
 * @see av_opt_find().
 */
public static native @Const AVClass avfilter_get_class();

@Opaque public static class AVFilterGraphInternal extends Pointer {
    public AVFilterGraphInternal() { }
    public AVFilterGraphInternal(Pointer p) { super(p); }
}

/**
 * A function pointer passed to the @ref AVFilterGraph.execute callback to be
 * executed multiple times, possibly in parallel.
 *
 * @param ctx the filter context the job belongs to
 * @param arg an opaque parameter passed through from @ref
 *            AVFilterGraph.execute
 * @param jobnr the index of the job being executed
 * @param nb_jobs the total number of jobs
 *
 * @return 0 on success, a negative AVERROR on error
 */
public static class avfilter_action_func extends FunctionPointer {
    static { Loader.load(); }
    public    avfilter_action_func(Pointer p) { super(p); }
    protected avfilter_action_func() { allocate(); }
    private native void allocate();
    public native int call(AVFilterContext ctx, Pointer arg, int jobnr, int nb_jobs);
}

/**
 * A function executing multiple jobs, possibly in parallel.
 *
 * @param ctx the filter context to which the jobs belong
 * @param func the function to be called multiple times
 * @param arg the argument to be passed to func
 * @param ret a nb_jobs-sized array to be filled with return values from each
 *            invocation of func
 * @param nb_jobs the number of jobs to execute
 *
 * @return 0 on success, a negative AVERROR on error
 */
public static class avfilter_execute_func extends FunctionPointer {
    static { Loader.load(); }
    public    avfilter_execute_func(Pointer p) { super(p); }
    protected avfilter_execute_func() { allocate(); }
    private native void allocate();
    public native int call(AVFilterContext ctx, avfilter_action_func func,
                                    Pointer arg, IntPointer ret, int nb_jobs);
}

public static class AVFilterGraph extends Pointer {
    static { Loader.load(); }
    public AVFilterGraph() { allocate(); }
    public AVFilterGraph(int size) { allocateArray(size); }
    public AVFilterGraph(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterGraph position(int position) {
        return (AVFilterGraph)super.position(position);
    }

    @MemberGetter public native @Const AVClass av_class();
// #if FF_API_FOO_COUNT
    public native @Cast("unsigned") @Deprecated int filter_count_unused(); public native AVFilterGraph filter_count_unused(int filter_count_unused);
// #endif
    public native AVFilterContext filters(int i); public native AVFilterGraph filters(int i, AVFilterContext filters);
    @MemberGetter public native @Cast("AVFilterContext**") PointerPointer filters();
// #if !FF_API_FOO_COUNT
// #endif

    /** sws options to use for the auto-inserted scale filters */
    public native @Cast("char*") BytePointer scale_sws_opts(); public native AVFilterGraph scale_sws_opts(BytePointer scale_sws_opts);
    /** libavresample options to use for the auto-inserted resample filters */
    public native @Cast("char*") BytePointer resample_lavr_opts(); public native AVFilterGraph resample_lavr_opts(BytePointer resample_lavr_opts);
// #if FF_API_FOO_COUNT
    public native @Cast("unsigned") int nb_filters(); public native AVFilterGraph nb_filters(int nb_filters);
// #endif

    /**
     * Type of multithreading allowed for filters in this graph. A combination
     * of AVFILTER_THREAD_* flags.
     *
     * May be set by the caller at any point, the setting will apply to all
     * filters initialized after that. The default is allowing everything.
     *
     * When a filter in this graph is initialized, this field is combined using
     * bit AND with AVFilterContext.thread_type to get the final mask used for
     * determining allowed threading types. I.e. a threading type needs to be
     * set in both to be allowed.
     */
    public native int thread_type(); public native AVFilterGraph thread_type(int thread_type);

    /**
     * Maximum number of threads used by filters in this graph. May be set by
     * the caller before adding any filters to the filtergraph. Zero (the
     * default) means that the number of threads is determined automatically.
     */
    public native int nb_threads(); public native AVFilterGraph nb_threads(int nb_threads);

    /**
     * Opaque object for libavfilter internal use.
     */
    public native AVFilterGraphInternal internal(); public native AVFilterGraph internal(AVFilterGraphInternal internal);

    /**
     * Opaque user data. May be set by the caller to an arbitrary value, e.g. to
     * be used from callbacks like @ref AVFilterGraph.execute.
     * Libavfilter will not touch this field in any way.
     */
    public native Pointer opaque(); public native AVFilterGraph opaque(Pointer opaque);

    /**
     * This callback may be set by the caller immediately after allocating the
     * graph and before adding any filters to it, to provide a custom
     * multithreading implementation.
     *
     * If set, filters with slice threading capability will call this callback
     * to execute multiple jobs in parallel.
     *
     * If this field is left unset, libavfilter will use its internal
     * implementation, which may or may not be multithreaded depending on the
     * platform and build options.
     */
    public native avfilter_execute_func execute(); public native AVFilterGraph execute(avfilter_execute_func execute);

    /** swr options to use for the auto-inserted aresample filters, Access ONLY through AVOptions */
    public native @Cast("char*") BytePointer aresample_swr_opts(); public native AVFilterGraph aresample_swr_opts(BytePointer aresample_swr_opts);

    /**
     * Private fields
     *
     * The following fields are for internal use only.
     * Their type, offset, number and semantic can change without notice.
     */

    public native AVFilterLink sink_links(int i); public native AVFilterGraph sink_links(int i, AVFilterLink sink_links);
    @MemberGetter public native @Cast("AVFilterLink**") PointerPointer sink_links();
    public native int sink_links_count(); public native AVFilterGraph sink_links_count(int sink_links_count);

    public native @Cast("unsigned") int disable_auto_convert(); public native AVFilterGraph disable_auto_convert(int disable_auto_convert);
}

/**
 * Allocate a filter graph.
 */
public static native AVFilterGraph avfilter_graph_alloc();

/**
 * Create a new filter instance in a filter graph.
 *
 * @param graph graph in which the new filter will be used
 * @param filter the filter to create an instance of
 * @param name Name to give to the new instance (will be copied to
 *             AVFilterContext.name). This may be used by the caller to identify
 *             different filters, libavfilter itself assigns no semantics to
 *             this parameter. May be NULL.
 *
 * @return the context of the newly created filter instance (note that it is
 *         also retrievable directly through AVFilterGraph.filters or with
 *         avfilter_graph_get_filter()) on success or NULL or failure.
 */
public static native AVFilterContext avfilter_graph_alloc_filter(AVFilterGraph graph,
                                             @Const AVFilter filter,
                                             @Cast("const char*") BytePointer name);
public static native AVFilterContext avfilter_graph_alloc_filter(AVFilterGraph graph,
                                             @Const AVFilter filter,
                                             String name);

/**
 * Get a filter instance with name name from graph.
 *
 * @return the pointer to the found filter instance or NULL if it
 * cannot be found.
 */
public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph graph, @Cast("char*") BytePointer name);
public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph graph, @Cast("char*") ByteBuffer name);
public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph graph, @Cast("char*") byte[] name);

// #if FF_API_AVFILTER_OPEN
/**
 * Add an existing filter instance to a filter graph.
 *
 * @param graphctx  the filter graph
 * @param filter the filter to be added
 *
 * @deprecated use avfilter_graph_alloc_filter() to allocate a filter in a
 * filter graph
 */
public static native @Deprecated int avfilter_graph_add_filter(AVFilterGraph graphctx, AVFilterContext filter);
// #endif

/**
 * Create and add a filter instance into an existing graph.
 * The filter instance is created from the filter filt and inited
 * with the parameters args and opaque.
 *
 * In case of success put in *filt_ctx the pointer to the created
 * filter instance, otherwise set *filt_ctx to NULL.
 *
 * @param name the instance name to give to the created filter instance
 * @param graph_ctx the filter graph
 * @return a negative AVERROR error code in case of failure, a non
 * negative value otherwise
 */
public static native int avfilter_graph_create_filter(@Cast("AVFilterContext**") PointerPointer filt_ctx, @Const AVFilter filt,
                                 @Cast("const char*") BytePointer name, @Cast("const char*") BytePointer args, Pointer opaque,
                                 AVFilterGraph graph_ctx);
public static native int avfilter_graph_create_filter(@ByPtrPtr AVFilterContext filt_ctx, @Const AVFilter filt,
                                 @Cast("const char*") BytePointer name, @Cast("const char*") BytePointer args, Pointer opaque,
                                 AVFilterGraph graph_ctx);
public static native int avfilter_graph_create_filter(@ByPtrPtr AVFilterContext filt_ctx, @Const AVFilter filt,
                                 String name, String args, Pointer opaque,
                                 AVFilterGraph graph_ctx);

/**
 * Enable or disable automatic format conversion inside the graph.
 *
 * Note that format conversion can still happen inside explicitly inserted
 * scale and aresample filters.
 *
 * @param flags  any of the AVFILTER_AUTO_CONVERT_* constants
 */
public static native void avfilter_graph_set_auto_convert(AVFilterGraph graph, @Cast("unsigned") int flags);

/** enum  */
public static final int
    /** all automatic conversions enabled */
    AVFILTER_AUTO_CONVERT_ALL  = 0,
    /** all automatic conversions disabled */
    AVFILTER_AUTO_CONVERT_NONE = -1;

/**
 * Check validity and configure all the links and formats in the graph.
 *
 * @param graphctx the filter graph
 * @param log_ctx context used for logging
 * @return >= 0 in case of success, a negative AVERROR code otherwise
 */
public static native int avfilter_graph_config(AVFilterGraph graphctx, Pointer log_ctx);

/**
 * Free a graph, destroy its links, and set *graph to NULL.
 * If *graph is NULL, do nothing.
 */
public static native void avfilter_graph_free(@Cast("AVFilterGraph**") PointerPointer graph);
public static native void avfilter_graph_free(@ByPtrPtr AVFilterGraph graph);

/**
 * A linked-list of the inputs/outputs of the filter chain.
 *
 * This is mainly useful for avfilter_graph_parse() / avfilter_graph_parse2(),
 * where it is used to communicate open (unlinked) inputs and outputs from and
 * to the caller.
 * This struct specifies, per each not connected pad contained in the graph, the
 * filter context and the pad index required for establishing a link.
 */
public static class AVFilterInOut extends Pointer {
    static { Loader.load(); }
    public AVFilterInOut() { allocate(); }
    public AVFilterInOut(int size) { allocateArray(size); }
    public AVFilterInOut(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVFilterInOut position(int position) {
        return (AVFilterInOut)super.position(position);
    }

    /** unique name for this input/output in the list */
    public native @Cast("char*") BytePointer name(); public native AVFilterInOut name(BytePointer name);

    /** filter context associated to this input/output */
    public native AVFilterContext filter_ctx(); public native AVFilterInOut filter_ctx(AVFilterContext filter_ctx);

    /** index of the filt_ctx pad to use for linking */
    public native int pad_idx(); public native AVFilterInOut pad_idx(int pad_idx);

    /** next input/input in the list, NULL if this is the last */
    public native AVFilterInOut next(); public native AVFilterInOut next(AVFilterInOut next);
}

/**
 * Allocate a single AVFilterInOut entry.
 * Must be freed with avfilter_inout_free().
 * @return allocated AVFilterInOut on success, NULL on failure.
 */
public static native AVFilterInOut avfilter_inout_alloc();

/**
 * Free the supplied list of AVFilterInOut and set *inout to NULL.
 * If *inout is NULL, do nothing.
 */
public static native void avfilter_inout_free(@Cast("AVFilterInOut**") PointerPointer inout);
public static native void avfilter_inout_free(@ByPtrPtr AVFilterInOut inout);

// #if AV_HAVE_INCOMPATIBLE_LIBAV_ABI || !FF_API_OLD_GRAPH_PARSE
// #else
/**
 * Add a graph described by a string to a graph.
 *
 * @param graph   the filter graph where to link the parsed graph context
 * @param filters string to be parsed
 * @param inputs  pointer to a linked list to the inputs of the graph, may be NULL.
 *                If non-NULL, *inputs is updated to contain the list of open inputs
 *                after the parsing, should be freed with avfilter_inout_free().
 * @param outputs pointer to a linked list to the outputs of the graph, may be NULL.
 *                If non-NULL, *outputs is updated to contain the list of open outputs
 *                after the parsing, should be freed with avfilter_inout_free().
 * @return non negative on success, a negative AVERROR code on error
 * @deprecated Use avfilter_graph_parse_ptr() instead.
 */
public static native @Deprecated int avfilter_graph_parse(AVFilterGraph graph, @Cast("const char*") BytePointer filters,
                         @Cast("AVFilterInOut**") PointerPointer inputs, @Cast("AVFilterInOut**") PointerPointer outputs,
                         Pointer log_ctx);
public static native @Deprecated int avfilter_graph_parse(AVFilterGraph graph, @Cast("const char*") BytePointer filters,
                         @ByPtrPtr AVFilterInOut inputs, @ByPtrPtr AVFilterInOut outputs,
                         Pointer log_ctx);
public static native @Deprecated int avfilter_graph_parse(AVFilterGraph graph, String filters,
                         @ByPtrPtr AVFilterInOut inputs, @ByPtrPtr AVFilterInOut outputs,
                         Pointer log_ctx);
// #endif

/**
 * Add a graph described by a string to a graph.
 *
 * @param graph   the filter graph where to link the parsed graph context
 * @param filters string to be parsed
 * @param inputs  pointer to a linked list to the inputs of the graph, may be NULL.
 *                If non-NULL, *inputs is updated to contain the list of open inputs
 *                after the parsing, should be freed with avfilter_inout_free().
 * @param outputs pointer to a linked list to the outputs of the graph, may be NULL.
 *                If non-NULL, *outputs is updated to contain the list of open outputs
 *                after the parsing, should be freed with avfilter_inout_free().
 * @return non negative on success, a negative AVERROR code on error
 */
public static native int avfilter_graph_parse_ptr(AVFilterGraph graph, @Cast("const char*") BytePointer filters,
                             @Cast("AVFilterInOut**") PointerPointer inputs, @Cast("AVFilterInOut**") PointerPointer outputs,
                             Pointer log_ctx);
public static native int avfilter_graph_parse_ptr(AVFilterGraph graph, @Cast("const char*") BytePointer filters,
                             @ByPtrPtr AVFilterInOut inputs, @ByPtrPtr AVFilterInOut outputs,
                             Pointer log_ctx);
public static native int avfilter_graph_parse_ptr(AVFilterGraph graph, String filters,
                             @ByPtrPtr AVFilterInOut inputs, @ByPtrPtr AVFilterInOut outputs,
                             Pointer log_ctx);

/**
 * Add a graph described by a string to a graph.
 *
 * @param[in]  graph   the filter graph where to link the parsed graph context
 * @param[in]  filters string to be parsed
 * @param[out] inputs  a linked list of all free (unlinked) inputs of the
 *                     parsed graph will be returned here. It is to be freed
 *                     by the caller using avfilter_inout_free().
 * @param[out] outputs a linked list of all free (unlinked) outputs of the
 *                     parsed graph will be returned here. It is to be freed by the
 *                     caller using avfilter_inout_free().
 * @return zero on success, a negative AVERROR code on error
 *
 * @note This function returns the inputs and outputs that are left
 * unlinked after parsing the graph and the caller then deals with
 * them.
 * @note This function makes no reference whatsoever to already
 * existing parts of the graph and the inputs parameter will on return
 * contain inputs of the newly parsed part of the graph.  Analogously
 * the outputs parameter will contain outputs of the newly created
 * filters.
 */
public static native int avfilter_graph_parse2(AVFilterGraph graph, @Cast("const char*") BytePointer filters,
                          @Cast("AVFilterInOut**") PointerPointer inputs,
                          @Cast("AVFilterInOut**") PointerPointer outputs);
public static native int avfilter_graph_parse2(AVFilterGraph graph, @Cast("const char*") BytePointer filters,
                          @ByPtrPtr AVFilterInOut inputs,
                          @ByPtrPtr AVFilterInOut outputs);
public static native int avfilter_graph_parse2(AVFilterGraph graph, String filters,
                          @ByPtrPtr AVFilterInOut inputs,
                          @ByPtrPtr AVFilterInOut outputs);

/**
 * Send a command to one or more filter instances.
 *
 * @param graph  the filter graph
 * @param target the filter(s) to which the command should be sent
 *               "all" sends to all filters
 *               otherwise it can be a filter or filter instance name
 *               which will send the command to all matching filters.
 * @param cmd    the command to send, for handling simplicity all commands must be alphanumeric only
 * @param arg    the argument for the command
 * @param res    a buffer with size res_size where the filter(s) can return a response.
 *
 * @returns >=0 on success otherwise an error code.
 *              AVERROR(ENOSYS) on unsupported commands
 */
public static native int avfilter_graph_send_command(AVFilterGraph graph, @Cast("const char*") BytePointer target, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") BytePointer res, int res_len, int flags);
public static native int avfilter_graph_send_command(AVFilterGraph graph, String target, String cmd, String arg, @Cast("char*") ByteBuffer res, int res_len, int flags);
public static native int avfilter_graph_send_command(AVFilterGraph graph, @Cast("const char*") BytePointer target, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") byte[] res, int res_len, int flags);
public static native int avfilter_graph_send_command(AVFilterGraph graph, String target, String cmd, String arg, @Cast("char*") BytePointer res, int res_len, int flags);
public static native int avfilter_graph_send_command(AVFilterGraph graph, @Cast("const char*") BytePointer target, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, @Cast("char*") ByteBuffer res, int res_len, int flags);
public static native int avfilter_graph_send_command(AVFilterGraph graph, String target, String cmd, String arg, @Cast("char*") byte[] res, int res_len, int flags);

/**
 * Queue a command for one or more filter instances.
 *
 * @param graph  the filter graph
 * @param target the filter(s) to which the command should be sent
 *               "all" sends to all filters
 *               otherwise it can be a filter or filter instance name
 *               which will send the command to all matching filters.
 * @param cmd    the command to sent, for handling simplicity all commands must be alphanummeric only
 * @param arg    the argument for the command
 * @param ts     time at which the command should be sent to the filter
 *
 * @note As this executes commands after this function returns, no return code
 *       from the filter is provided, also AVFILTER_CMD_FLAG_ONE is not supported.
 */
public static native int avfilter_graph_queue_command(AVFilterGraph graph, @Cast("const char*") BytePointer target, @Cast("const char*") BytePointer cmd, @Cast("const char*") BytePointer arg, int flags, double ts);
public static native int avfilter_graph_queue_command(AVFilterGraph graph, String target, String cmd, String arg, int flags, double ts);


/**
 * Dump a graph into a human-readable string representation.
 *
 * @param graph    the graph to dump
 * @param options  formatting options; currently ignored
 * @return  a string, or NULL in case of memory allocation failure;
 *          the string must be freed using av_free
 */
public static native @Cast("char*") BytePointer avfilter_graph_dump(AVFilterGraph graph, @Cast("const char*") BytePointer options);
public static native @Cast("char*") ByteBuffer avfilter_graph_dump(AVFilterGraph graph, String options);

/**
 * Request a frame on the oldest sink link.
 *
 * If the request returns AVERROR_EOF, try the next.
 *
 * Note that this function is not meant to be the sole scheduling mechanism
 * of a filtergraph, only a convenience function to help drain a filtergraph
 * in a balanced way under normal circumstances.
 *
 * Also note that AVERROR_EOF does not mean that frames did not arrive on
 * some of the sinks during the process.
 * When there are multiple sink links, in case the requested link
 * returns an EOF, this may cause a filter to flush pending frames
 * which are sent to another sink link, although unrequested.
 *
 * @return  the return value of ff_request_frame(),
 *          or AVERROR_EOF if all links returned AVERROR_EOF
 */
public static native int avfilter_graph_request_oldest(AVFilterGraph graph);

/**
 * @}
 */

// #endif /* AVFILTER_AVFILTER_H */


// Parsed from <libavfilter/buffersink.h>

/*
 * This file is part of FFmpeg.
 *
 * FFmpeg is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * FFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with FFmpeg; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

// #ifndef AVFILTER_BUFFERSINK_H
// #define AVFILTER_BUFFERSINK_H

/**
 * @file
 * @ingroup lavfi_buffersink
 * memory buffer sink API for audio and video
 */

// #include "avfilter.h"

/**
 * @defgroup lavfi_buffersink Buffer sink API
 * @ingroup lavfi
 * @{
 */

// #if FF_API_AVFILTERBUFFER
/**
 * Get an audio/video buffer data from buffer_sink and put it in bufref.
 *
 * This function works with both audio and video buffer sinks.
 *
 * @param buffer_sink pointer to a buffersink or abuffersink context
 * @param flags a combination of AV_BUFFERSINK_FLAG_* flags
 * @return >= 0 in case of success, a negative AVERROR code in case of
 * failure
 */
public static native @Deprecated int av_buffersink_get_buffer_ref(AVFilterContext buffer_sink,
                                 @Cast("AVFilterBufferRef**") PointerPointer bufref, int flags);
public static native @Deprecated int av_buffersink_get_buffer_ref(AVFilterContext buffer_sink,
                                 @ByPtrPtr AVFilterBufferRef bufref, int flags);

/**
 * Get the number of immediately available frames.
 */
public static native @Deprecated int av_buffersink_poll_frame(AVFilterContext ctx);

/**
 * Get a buffer with filtered data from sink and put it in buf.
 *
 * @param ctx pointer to a context of a buffersink or abuffersink AVFilter.
 * @param buf pointer to the buffer will be written here if buf is non-NULL. buf
 *            must be freed by the caller using avfilter_unref_buffer().
 *            Buf may also be NULL to query whether a buffer is ready to be
 *            output.
 *
 * @return >= 0 in case of success, a negative AVERROR code in case of
 *         failure.
 */
public static native @Deprecated int av_buffersink_read(AVFilterContext ctx, @Cast("AVFilterBufferRef**") PointerPointer buf);
public static native @Deprecated int av_buffersink_read(AVFilterContext ctx, @ByPtrPtr AVFilterBufferRef buf);

/**
 * Same as av_buffersink_read, but with the ability to specify the number of
 * samples read. This function is less efficient than av_buffersink_read(),
 * because it copies the data around.
 *
 * @param ctx pointer to a context of the abuffersink AVFilter.
 * @param buf pointer to the buffer will be written here if buf is non-NULL. buf
 *            must be freed by the caller using avfilter_unref_buffer(). buf
 *            will contain exactly nb_samples audio samples, except at the end
 *            of stream, when it can contain less than nb_samples.
 *            Buf may also be NULL to query whether a buffer is ready to be
 *            output.
 *
 * @warning do not mix this function with av_buffersink_read(). Use only one or
 * the other with a single sink, not both.
 */
public static native @Deprecated int av_buffersink_read_samples(AVFilterContext ctx, @Cast("AVFilterBufferRef**") PointerPointer buf,
                               int nb_samples);
public static native @Deprecated int av_buffersink_read_samples(AVFilterContext ctx, @ByPtrPtr AVFilterBufferRef buf,
                               int nb_samples);
// #endif

/**
 * Get a frame with filtered data from sink and put it in frame.
 *
 * @param ctx    pointer to a buffersink or abuffersink filter context.
 * @param frame  pointer to an allocated frame that will be filled with data.
 *               The data must be freed using av_frame_unref() / av_frame_free()
 * @param flags  a combination of AV_BUFFERSINK_FLAG_* flags
 *
 * @return  >= 0 in for success, a negative AVERROR code for failure.
 */
public static native int av_buffersink_get_frame_flags(AVFilterContext ctx, AVFrame frame, int flags);

/**
 * Tell av_buffersink_get_buffer_ref() to read video/samples buffer
 * reference, but not remove it from the buffer. This is useful if you
 * need only to read a video/samples buffer, without to fetch it.
 */
public static final int AV_BUFFERSINK_FLAG_PEEK = 1;

/**
 * Tell av_buffersink_get_buffer_ref() not to request a frame from its input.
 * If a frame is already buffered, it is read (and removed from the buffer),
 * but if no frame is present, return AVERROR(EAGAIN).
 */
public static final int AV_BUFFERSINK_FLAG_NO_REQUEST = 2;

/**
 * Struct to use for initializing a buffersink context.
 */
public static class AVBufferSinkParams extends Pointer {
    static { Loader.load(); }
    public AVBufferSinkParams() { allocate(); }
    public AVBufferSinkParams(int size) { allocateArray(size); }
    public AVBufferSinkParams(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVBufferSinkParams position(int position) {
        return (AVBufferSinkParams)super.position(position);
    }

    /** list of allowed pixel formats, terminated by AV_PIX_FMT_NONE */
    @MemberGetter public native @Cast("const AVPixelFormat*") IntPointer pixel_fmts();
}

/**
 * Create an AVBufferSinkParams structure.
 *
 * Must be freed with av_free().
 */
public static native AVBufferSinkParams av_buffersink_params_alloc();

/**
 * Struct to use for initializing an abuffersink context.
 */
public static class AVABufferSinkParams extends Pointer {
    static { Loader.load(); }
    public AVABufferSinkParams() { allocate(); }
    public AVABufferSinkParams(int size) { allocateArray(size); }
    public AVABufferSinkParams(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AVABufferSinkParams position(int position) {
        return (AVABufferSinkParams)super.position(position);
    }

    /** list of allowed sample formats, terminated by AV_SAMPLE_FMT_NONE */
    @MemberGetter public native @Cast("const AVSampleFormat*") IntPointer sample_fmts();
    /** list of allowed channel layouts, terminated by -1 */
    @MemberGetter public native @Const LongPointer channel_layouts();
    /** list of allowed channel counts, terminated by -1 */
    @MemberGetter public native @Const IntPointer channel_counts();
    /** if not 0, accept any channel count or layout */
    public native int all_channel_counts(); public native AVABufferSinkParams all_channel_counts(int all_channel_counts);
    /** list of allowed sample rates, terminated by -1 */
    public native IntPointer sample_rates(); public native AVABufferSinkParams sample_rates(IntPointer sample_rates);
}

/**
 * Create an AVABufferSinkParams structure.
 *
 * Must be freed with av_free().
 */
public static native AVABufferSinkParams av_abuffersink_params_alloc();

/**
 * Set the frame size for an audio buffer sink.
 *
 * All calls to av_buffersink_get_buffer_ref will return a buffer with
 * exactly the specified number of samples, or AVERROR(EAGAIN) if there is
 * not enough. The last buffer at EOF will be padded with 0.
 */
public static native void av_buffersink_set_frame_size(AVFilterContext ctx, @Cast("unsigned") int frame_size);

/**
 * Get the frame rate of the input.
 */
public static native @ByVal AVRational av_buffersink_get_frame_rate(AVFilterContext ctx);

/**
 * Get a frame with filtered data from sink and put it in frame.
 *
 * @param ctx pointer to a context of a buffersink or abuffersink AVFilter.
 * @param frame pointer to an allocated frame that will be filled with data.
 *              The data must be freed using av_frame_unref() / av_frame_free()
 *
 * @return
 *         - >= 0 if a frame was successfully returned.
 *         - AVERROR(EAGAIN) if no frames are available at this point; more
 *           input frames must be added to the filtergraph to get more output.
 *         - AVERROR_EOF if there will be no more output frames on this sink.
 *         - A different negative AVERROR code in other failure cases.
 */
public static native int av_buffersink_get_frame(AVFilterContext ctx, AVFrame frame);

/**
 * Same as av_buffersink_get_frame(), but with the ability to specify the number
 * of samples read. This function is less efficient than
 * av_buffersink_get_frame(), because it copies the data around.
 *
 * @param ctx pointer to a context of the abuffersink AVFilter.
 * @param frame pointer to an allocated frame that will be filled with data.
 *              The data must be freed using av_frame_unref() / av_frame_free()
 *              frame will contain exactly nb_samples audio samples, except at
 *              the end of stream, when it can contain less than nb_samples.
 *
 * @return The return codes have the same meaning as for
 *         av_buffersink_get_samples().
 *
 * @warning do not mix this function with av_buffersink_get_frame(). Use only one or
 * the other with a single sink, not both.
 */
public static native int av_buffersink_get_samples(AVFilterContext ctx, AVFrame frame, int nb_samples);

/**
 * @}
 */

// #endif /* AVFILTER_BUFFERSINK_H */


// Parsed from <libavfilter/buffersrc.h>

/*
 *
 * This file is part of FFmpeg.
 *
 * FFmpeg is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * FFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with FFmpeg; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

// #ifndef AVFILTER_BUFFERSRC_H
// #define AVFILTER_BUFFERSRC_H

/**
 * @file
 * @ingroup lavfi_buffersrc
 * Memory buffer source API.
 */

// #include "libavcodec/avcodec.h"
// #include "avfilter.h"

/**
 * @defgroup lavfi_buffersrc Buffer source API
 * @ingroup lavfi
 * @{
 */

/** enum  */
public static final int

    /**
     * Do not check for format changes.
     */
    AV_BUFFERSRC_FLAG_NO_CHECK_FORMAT = 1,

// #if FF_API_AVFILTERBUFFER
    /**
     * Ignored
     */
    AV_BUFFERSRC_FLAG_NO_COPY = 2,
// #endif

    /**
     * Immediately push the frame to the output.
     */
    AV_BUFFERSRC_FLAG_PUSH = 4,

    /**
     * Keep a reference to the frame.
     * If the frame if reference-counted, create a new reference; otherwise
     * copy the frame data.
     */
    AV_BUFFERSRC_FLAG_KEEP_REF = 8;

/**
 * Add buffer data in picref to buffer_src.
 *
 * @param buffer_src  pointer to a buffer source context
 * @param picref      a buffer reference, or NULL to mark EOF
 * @param flags       a combination of AV_BUFFERSRC_FLAG_*
 * @return            >= 0 in case of success, a negative AVERROR code
 *                    in case of failure
 */
public static native int av_buffersrc_add_ref(AVFilterContext buffer_src,
                         AVFilterBufferRef picref, int flags);

/**
 * Get the number of failed requests.
 *
 * A failed request is when the request_frame method is called while no
 * frame is present in the buffer.
 * The number is reset when a frame is added.
 */
public static native @Cast("unsigned") int av_buffersrc_get_nb_failed_requests(AVFilterContext buffer_src);

// #if FF_API_AVFILTERBUFFER
/**
 * Add a buffer to a filtergraph.
 *
 * @param ctx an instance of the buffersrc filter
 * @param buf buffer containing frame data to be passed down the filtergraph.
 * This function will take ownership of buf, the user must not free it.
 * A NULL buf signals EOF -- i.e. no more frames will be sent to this filter.
 *
 * @deprecated use av_buffersrc_write_frame() or av_buffersrc_add_frame()
 */
public static native @Deprecated int av_buffersrc_buffer(AVFilterContext ctx, AVFilterBufferRef buf);
// #endif

/**
 * Add a frame to the buffer source.
 *
 * @param ctx   an instance of the buffersrc filter
 * @param frame frame to be added. If the frame is reference counted, this
 * function will make a new reference to it. Otherwise the frame data will be
 * copied.
 *
 * @return 0 on success, a negative AVERROR on error
 *
 * This function is equivalent to av_buffersrc_add_frame_flags() with the
 * AV_BUFFERSRC_FLAG_KEEP_REF flag.
 */
public static native int av_buffersrc_write_frame(AVFilterContext ctx, @Const AVFrame frame);

/**
 * Add a frame to the buffer source.
 *
 * @param ctx   an instance of the buffersrc filter
 * @param frame frame to be added. If the frame is reference counted, this
 * function will take ownership of the reference(s) and reset the frame.
 * Otherwise the frame data will be copied. If this function returns an error,
 * the input frame is not touched.
 *
 * @return 0 on success, a negative AVERROR on error.
 *
 * @note the difference between this function and av_buffersrc_write_frame() is
 * that av_buffersrc_write_frame() creates a new reference to the input frame,
 * while this function takes ownership of the reference passed to it.
 *
 * This function is equivalent to av_buffersrc_add_frame_flags() without the
 * AV_BUFFERSRC_FLAG_KEEP_REF flag.
 */
public static native int av_buffersrc_add_frame(AVFilterContext ctx, AVFrame frame);

/**
 * Add a frame to the buffer source.
 *
 * By default, if the frame is reference-counted, this function will take
 * ownership of the reference(s) and reset the frame. This can be controled
 * using the flags.
 *
 * If this function returns an error, the input frame is not touched.
 *
 * @param buffer_src  pointer to a buffer source context
 * @param frame       a frame, or NULL to mark EOF
 * @param flags       a combination of AV_BUFFERSRC_FLAG_*
 * @return            >= 0 in case of success, a negative AVERROR code
 *                    in case of failure
 */
public static native int av_buffersrc_add_frame_flags(AVFilterContext buffer_src,
                                 AVFrame frame, int flags);


/**
 * @}
 */

// #endif /* AVFILTER_BUFFERSRC_H */


}
