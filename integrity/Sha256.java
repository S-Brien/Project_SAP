import java.io.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;




class Sha256{

    public static void main(String [] args){

        String file = "getsha.py";

        String path = "../out";
        System.out.println("sha 256:" + checkSum(file));
        //listFiles(path);

        String folderToZip = "../out";
        //String zipName = "../out/test.zip";
        String zipName = "test.zip";
        try{

            zipFolder(Paths.get(folderToZip), Paths.get(zipName));

        }catch(Exception e){

        }
        System.out.println("######################");
        System.out.println("test.zip "+checkSum(zipName));
        System.out.println("########################");

   }

    private static void zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();
    }


    public static String checkSum(String path){
        String checksum = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while( (numOfBytesRead = fis.read(buffer)) > 0){
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }

        return checksum;
    }
    public static String checkSumB(String s){
        String checksum = "";
        try{
            MessageDigest m=MessageDigest.getInstance("SHA-256");
            m.update(s.getBytes(),0,s.length());
            checksum = new BigInteger(1,m.digest()).toString(16);

            System.out.println("sha 256: "+ checksum);

        }catch(Exception e){

        }
        return checksum;
    }

    public static byte[] getBytesFromFile(String path){
        byte [] b = null;
        try{
            b = Files.readAllBytes(Paths.get(path));

        }catch(Exception e){

        }


        return b;
    }

    public static void listFiles(String startDir) {
        File dir = new File(startDir);
        File[] files = dir.listFiles();
        byte[] myb = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles(file.getAbsolutePath());
                } else {
                   //System.out.println(file.getAbsolutePath() + " (size in bytes: " + file.length()+")");
                   System.out.println(checkSum(file.getAbsolutePath()));
                }
            }

        }


    }

}