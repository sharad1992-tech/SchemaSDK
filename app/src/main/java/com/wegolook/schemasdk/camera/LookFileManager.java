package com.wegolook.schemasdk.camera;

import android.content.Context;

import com.wegolook.schemasdk.util.Files;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;


/**
 *   on 7/14/16.
 */
public class LookFileManager {
    public static String JPEG = "jpg";

    private String lookId;
    private Context context;

    public LookFileManager(Context context, String lookId) {
        this.context = context;
        this.lookId = lookId;
    }

    public File createFile(String extension) {
        File lookDir = getLookDirectory();
        String fileName = String.format(
                "%s.%s",
                UUID.randomUUID().toString().toLowerCase(),
                extension
        );
        return new File(lookDir, fileName);
    }



    public void renameFile(String oldName, String newName) {
        File lookDir = getLookDirectory();
        File oldFile = new File(lookDir, oldName);
        if(oldFile.exists()) {
            File newFile = new File(lookDir, newName);
            oldFile.renameTo(newFile);
        }
    }

    private File getLookDirectory() {
        return context.getDir(lookId, Context.MODE_PRIVATE);
    }


    public Observable<File> getLocalFile(String fileName) {
        File lookDirectory = getLookDirectory();
        File file = new File(lookDirectory, fileName);
        return Observable.just(file);
    }

    public void deleteAllFilesForLook() {
        File file = getLookDirectory();
        if(file.exists()) Files.deleteDir(file);
    }
}
