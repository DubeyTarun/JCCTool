package StepDefinitions;

import io.cucumber.java.hu.Ha;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MyStepsActions extends Constants implements NonThemedShowsName, ThemedShowsName, HostingShowsName {

    int numberOfRows;
    int numberOfOldComicsRow;
    int numberOfNewComicsRow;
    int spotsForOldComics = numberOfOldComicSpots;
    int spotsForNewComics = numberOfNewComicSpots;
    ArrayList<String> comicInitialNames = new ArrayList<String>();
    ArrayList<Long> comicContactNumbers = new ArrayList<Long>();
    ArrayList<String> comicSelectedVenues = new ArrayList<String>();
    ArrayList<String> comicInstaId = new ArrayList<String>();
    ArrayList<String> oldComicNames = new ArrayList<String>();
    ArrayList<Integer> timesOldComicsPerformed = new ArrayList<Integer>();
    Map<String, Integer> oldComicsMap = new HashMap<String, Integer>();
    ArrayList<String> newComicNames = new ArrayList<String>();
    ArrayList<Integer> timesNewComicsPerformed = new ArrayList<Integer>();
    Map<String, Integer> newComicsMap = new HashMap<String, Integer>();
    ArrayList<String> firstTimeComicNames = new ArrayList<String>();
    ArrayList<String> venueSpecificComicName = new ArrayList<String>();
    Map<String, String> comicsMapWithMobileNumber = new HashMap<String, String>();
    Map<String, String> comicsMapWithInstaId = new HashMap<String, String>();

    String[] showNameArray;
    String[] sheetShowNameArray;
    int colNum = 0;

    public void identifyTypeOfToolRunning() {
        String name = sheetName;

        switch (name) {
            case "openMicSorting":
                showNameArray = venues;
                sheetShowNameArray = sheetVenue;
                break;
            case "nonThemedShowName":
                showNameArray = NonThemedShowNames;
                sheetShowNameArray = sheetNonThemedShowNames;
                break;
            case "themedShowName":
                showNameArray = themedShowNames;
                sheetShowNameArray = sheetThemedShowNames;
                colNum = 1;
                break;
            case "hostingShowName":
                showNameArray = hostingShowNames;
                sheetShowNameArray = sheetHostingShowNames;
                colNum = 2;
                break;
        }
    }


    public void collectOldComicsDataFromJCCDatabase() throws IOException {

        FileInputStream fs = new FileInputStream(pathForJCCDatabase);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet oldComicsInfo = wb.getSheetAt(0);

        numberOfOldComicsRow = oldComicsInfo.getLastRowNum();
        String oldComicName;
        int numberOftimesOldComicPerformed;


        for (int i = 0; i < numberOfOldComicsRow; i++) {
            oldComicName = String.valueOf(oldComicsInfo.getRow(i + 1).getCell(0));
            oldComicNames.add(i, oldComicName);
            numberOftimesOldComicPerformed = (int) oldComicsInfo.getRow(i + 1).getCell(1).getNumericCellValue();
            timesOldComicsPerformed.add(i, numberOftimesOldComicPerformed);
        }
    }

    public void combineOldComicsDataIntoMap() {
        for (int i = 0; i < numberOfOldComicsRow; i++) {
            oldComicsMap.put(oldComicNames.get(i), timesOldComicsPerformed.get(i));
        }
        oldComicsMap = sortMapByValue((HashMap<String, Integer>) oldComicsMap);
    }

    public void collectNewComicsDataFromJCCDatabase() throws IOException {
        FileInputStream fs = new FileInputStream(pathForJCCDatabase);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet newComicsInfo = wb.getSheetAt(1);

        numberOfNewComicsRow = newComicsInfo.getLastRowNum();
        String newComicName;
        int numberOftimesNewComicPerformed;


        for (int i = 0; i < numberOfNewComicsRow; i++) {
            newComicName = String.valueOf(newComicsInfo.getRow(i + 1).getCell(0));
            newComicNames.add(i, newComicName);
            numberOftimesNewComicPerformed = (int) newComicsInfo.getRow(i + 1).getCell(1).getNumericCellValue();
            timesNewComicsPerformed.add(i, numberOftimesNewComicPerformed);
        }
    }

    public void combineNewComicsDataIntoMap() {
        for (int i = 0; i < numberOfNewComicsRow; i++) {
            newComicsMap.put(newComicNames.get(i), timesNewComicsPerformed.get(i));
        }
        newComicsMap = sortMapByValue((HashMap<String, Integer>) newComicsMap);
    }

    public void combineComicsNameWithMobileNumber() {
        for (int i = 0; i < numberOfRows; i++) {
            String comicMobileAndInstaId = comicContactNumbers.get(i) + " --> " + comicInstaId.get(i);
            comicsMapWithMobileNumber.put(comicInitialNames.get(i), comicMobileAndInstaId);
        }
    }

    public boolean isOldComic(String name) {
        for (int i = 0; i < numberOfOldComicsRow; i++) {
            if (name.equalsIgnoreCase(oldComicNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void collectDataFromJCCResponse() throws IOException, NullPointerException {

        FileInputStream fs = new FileInputStream(pathForFormResponse);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet sheet1 = wb.getSheetAt(0);

        numberOfRows = sheet1.getLastRowNum();
        String comicName;
        long comicMobileNumber;
        String venuesSelected;
        String instaId;

        for (int i = 0; i < numberOfRows; i++) {
            comicName = String.valueOf(sheet1.getRow(i + 1).getCell(1));
            comicInitialNames.add(i, comicName);
            comicMobileNumber = Long.parseLong(String.valueOf((long) sheet1.getRow(i + 1).getCell(2).getNumericCellValue()));
            comicContactNumbers.add(i, comicMobileNumber);
            venuesSelected = String.valueOf(sheet1.getRow(i + 1).getCell(colNum + 4));
            comicSelectedVenues.add(i, venuesSelected);
            instaId = String.valueOf(sheet1.getRow(i + 1).getCell(3));
            comicInstaId.add(i, instaId);
        }

        FileOutputStream fos = new FileOutputStream(pathForFormResponse);
        wb.write(fos);
        fos.close();
    }

    public void databaseUpdate() throws IOException {

        for (int i = 0; i < numberOfRows; i++) {
            int oldComicCount = 0;
            int newComicCount = 0;
            String name = comicInitialNames.get(i);
            for (int j = 0; j < numberOfOldComicsRow; j++) {
                if (comicInitialNames.get(i).equalsIgnoreCase(oldComicNames.get(j))) {
                    oldComicCount++;
                    break;
                }
            }
            if (oldComicCount == 0) {
                for (int k = 0; k < numberOfNewComicsRow; k++) {
                    if (comicInitialNames.get(i).equalsIgnoreCase(newComicNames.get(k))) {
                        newComicCount++;
                        break;
                    }
                }
                if (newComicCount == 0) {
                    firstTimeComicNames.add(comicInitialNames.get(i));
                }
            }

        }
        if (!(firstTimeComicNames.isEmpty())) {
            updateNewComicInDatabase();
        }
    }

    public void updateNewComicInDatabase() throws IOException {
        FileInputStream fs = new FileInputStream(pathForJCCDatabase);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet newComicSheet = wb.getSheetAt(1);

        for (String firstTimeComicName : firstTimeComicNames) {
            newComicSheet.createRow(numberOfNewComicsRow + 1).createCell(0).setCellValue(firstTimeComicName);
            newComicSheet.getRow(numberOfNewComicsRow + 1).createCell(1).setCellValue(0);
            numberOfNewComicsRow++;
        }

        FileOutputStream fos = new FileOutputStream(pathForJCCDatabase);
        wb.write(fos);
        fos.close();

    }


    public void collectComicNamesForEachVenue(String number) throws IOException {
        FileInputStream fs = new FileInputStream(pathForJCCDatabase);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet oldComicsSheet = wb.getSheetAt(0);
        Sheet newComicSheet = wb.getSheetAt(1);

        ArrayList<String> venueSpecificOldComicName = new ArrayList<String>();
        ArrayList<String> venueSpecificNewComicName = new ArrayList<String>();


        for (int j = 0; j < comicSelectedVenues.size(); j++) {
            if (comicSelectedVenues.get(j).contains(showNameArray[Integer.parseInt(number) - 1])) {
                if (isOldComic(comicInitialNames.get(j))) {
                    venueSpecificOldComicName.add(comicInitialNames.get(j));
                } else {
                    venueSpecificNewComicName.add(comicInitialNames.get(j));
                }
            }
        }

        System.out.println("Old comics those who have applied for venue *" + sheetShowNameArray[Integer.parseInt(number) - 1] + "* are " + venueSpecificOldComicName);
        System.out.println("New comics those who have applied for venue *" + sheetShowNameArray[Integer.parseInt(number) - 1] + "* are " + venueSpecificNewComicName);

//        System.out.println("Comics those who have applied for venue "+sheetShowNameArray[Integer.parseInt(number)-1]+" are ");


        Iterator oldComicsIterator = oldComicsMap.entrySet().iterator();
        while (oldComicsIterator.hasNext() && (spotsForOldComics != 0)) {

            Map.Entry mapElement = (Map.Entry) oldComicsIterator.next();
            for (int i = 0; i < venueSpecificOldComicName.size(); i++) {
                if (mapElement.getKey().toString().equalsIgnoreCase(venueSpecificOldComicName.get(i))) {
                    venueSpecificComicName.add(mapElement.getKey().toString());
                    spotsForOldComics--;
                    for (int j = 0; j < numberOfOldComicsRow; j++) {
                        if (mapElement.getKey().toString().equalsIgnoreCase(String.valueOf(oldComicsSheet.getRow(j + 1).getCell(0)))) {
                            int numberOfTimes = (int) oldComicsSheet.getRow(j + 1).getCell(1).getNumericCellValue();
                            numberOfTimes++;
                            oldComicsSheet.getRow(j + 1).createCell(1).setCellValue(numberOfTimes);
                            break;
                        }
                    }
                    break;
                }
            }
        }

        Iterator newComicsIterator = newComicsMap.entrySet().iterator();
        while (newComicsIterator.hasNext() && (spotsForNewComics != 0)) {

            Map.Entry mapElement = (Map.Entry) newComicsIterator.next();
            for (int i = 0; i < venueSpecificNewComicName.size(); i++) {
                if (mapElement.getKey().toString().equalsIgnoreCase(venueSpecificNewComicName.get(i))) {
                    venueSpecificComicName.add(mapElement.getKey().toString());
                    spotsForNewComics--;
                    for (int j = 0; j < numberOfNewComicsRow; j++) {
                        if (mapElement.getKey().toString().equalsIgnoreCase(String.valueOf(newComicSheet.getRow(j + 1).getCell(0)))) {
                            int numberOfTimes = (int) newComicSheet.getRow(j + 1).getCell(1).getNumericCellValue();
                            numberOfTimes++;
                            newComicSheet.getRow(j + 1).createCell(1).setCellValue(numberOfTimes);
                            break;
                        }
                    }
                    break;
                }
            }
        }
//        System.out.println("Comics those who have got spots at venue " + number + " " + venueSpecificComicName);
        finalListOfSpotWithNamesAndMobileNumberAndInstaId(Integer.parseInt(number));
        FileOutputStream fos = new FileOutputStream(pathForJCCDatabase);
        wb.write(fos);
        fos.close();
    }

    public void finalListOfSpotWithNamesAndMobileNumberAndInstaId(int number) throws IOException {
        FileInputStream fs = new FileInputStream(pathForFormResponse);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet finalSpotSheet = wb.getSheet(sheetShowNameArray[number - 1]);

        Iterator finalComicsIterator = comicsMapWithMobileNumber.entrySet().iterator();
        while (finalComicsIterator.hasNext()) {

            Map.Entry mapElement = (Map.Entry) finalComicsIterator.next();
            for (int i = 0; i < venueSpecificComicName.size(); i++) {
                if (mapElement.getKey().toString().equalsIgnoreCase(venueSpecificComicName.get(i))) {
//                    finalSpotSheet.createRow(i+1).createCell(0).setCellValue(mapElement.getKey().toString());
                    System.out.print(mapElement.getKey().toString());
//                    finalSpotSheet.getRow(i+1).createCell(1).setCellValue(mapElement.getValue().toString());
                    System.out.print("-->" + mapElement.getValue().toString());
                    System.out.println("\n--------------------------");
                    break;
                }
            }
        }
        FileOutputStream fos = new FileOutputStream(pathForFormResponse);
        wb.write(fos);
        fos.close();
    }

    public void newSheetCreater() throws IOException {
        for (int i = 0; i < totalNumberOfOpenMics; i++) {
            FileInputStream fs = new FileInputStream(pathForFormResponse);
            Workbook wb = new XSSFWorkbook(fs);
            Sheet finalSpotSheet = wb.createSheet(sheetShowNameArray[i]);
            FileOutputStream fos = new FileOutputStream(pathForFormResponse);
            wb.write(fos);
            fos.close();
        }
    }
}
