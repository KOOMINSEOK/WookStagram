package FileHandler;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import User.User;

public class ThumnailPostMapper {

    private static String POST_FOLDER_PATH;

    public static File getPostFileForThumbnail(User user, int thumbnailIndex) {
    	POST_FOLDER_PATH = "users/" + user.getId()+"/Post";
    	
        File postFolder = new File(POST_FOLDER_PATH);
        if (!postFolder.exists() || !postFolder.isDirectory()) {
            throw new IllegalArgumentException("Invalid Post folder path.");
        }

        File[] postFiles = postFolder.listFiles((dir, name) -> name.startsWith("post") && name.endsWith(".dat"));
        if (postFiles == null || postFiles.length == 0) {
            throw new IllegalStateException("No post files found in the folder.");
        }

        List<File> sortedPostFiles = Arrays.stream(postFiles)
                .sorted(Comparator.comparingInt(file -> {
                    String fileName = ((File) file).getName();
                    String numberPart = fileName.replaceAll("\\D+", ""); 
                    return numberPart.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(numberPart);
                }))
                .collect(Collectors.toList());
        if (thumbnailIndex < 0 || thumbnailIndex >= sortedPostFiles.size()) {
            throw new IndexOutOfBoundsException("Invalid thumbnail index.");
        }

        return sortedPostFiles.get(thumbnailIndex);
    }
    
    public static int getLastPostNumber(String userId) {
        POST_FOLDER_PATH = "users/" + userId + "/Post";

        File postFolder = new File(POST_FOLDER_PATH);
        if (!postFolder.exists() || !postFolder.isDirectory()) {
            throw new IllegalArgumentException("Invalid Post folder path.");
        }
        File[] postFiles = postFolder.listFiles((dir, name) -> name.startsWith("post") && name.endsWith(".dat"));
        if (postFiles == null || postFiles.length == 0) {
            return 0; // 게시물이 없는 경우 0 반환
        }

        List<File> sortedPostFiles = Arrays.stream(postFiles)
                .sorted(Comparator.comparingInt(file -> {
                    String fileName = ((File) file).getName();
                    String numberPart = fileName.replaceAll("\\D+", ""); // 숫자만 추출
                    return numberPart.isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(numberPart);
                }))
                .collect(Collectors.toList());

        // 마지막 파일의 숫자 추출
        String lastFileName = sortedPostFiles.get(sortedPostFiles.size() - 1).getName();
        String lastNumberPart = lastFileName.replaceAll("\\D+", "");
        return lastNumberPart.isEmpty() ? 0 : Integer.parseInt(lastNumberPart);
    }
    
}
