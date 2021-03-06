package im.actor.core.api.rpc;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.runtime.bser.*;
import im.actor.runtime.collections.*;
import static im.actor.runtime.bser.Utils.*;
import im.actor.core.network.parser.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.google.j2objc.annotations.ObjectiveCName;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import im.actor.core.api.*;

public class ResponseDownloadSharedBlob extends Response {

    public static final int HEADER = 0xa67;
    public static ResponseDownloadSharedBlob fromBytes(byte[] data) throws IOException {
        return Bser.parse(new ResponseDownloadSharedBlob(), data);
    }

    private byte[] blob;

    public ResponseDownloadSharedBlob(@NotNull byte[] blob) {
        this.blob = blob;
    }

    public ResponseDownloadSharedBlob() {

    }

    @NotNull
    public byte[] getBlob() {
        return this.blob;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.blob = values.getBytes(1);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        if (this.blob == null) {
            throw new IOException();
        }
        writer.writeBytes(1, this.blob);
    }

    @Override
    public String toString() {
        String res = "tuple DownloadSharedBlob{";
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
