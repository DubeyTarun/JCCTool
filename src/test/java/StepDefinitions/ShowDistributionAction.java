package StepDefinitions;

import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class ShowDistributionAction extends ShowDistributionConstants {

    int numberOfRows = 0;
    ArrayList<String> nonThemedShows = new ArrayList<String>();
    ArrayList<String> themedShows = new ArrayList<String>();
    ArrayList<String> hostingShows = new ArrayList<String>();
    Map<String, String> nonThemedShowsMapWithSelectedComicsName = new LinkedHashMap<String, String>();
    Map<String, String> themedShowsMapWithSelectedComicsName = new LinkedHashMap<String, String>();
    Map<String, String> hostingShowsMapWithSelectedComicsName = new LinkedHashMap<String, String>();
    ArrayList<String> tempShowsName = new ArrayList<String>();
    Map<String, ArrayList<String>> comicsMappedWithNonThemedShows = new LinkedHashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> comicsMappedWithThemedShows = new LinkedHashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> comicsMappedWithHostingShows = new LinkedHashMap<String, ArrayList<String>>();

    public void addShowsNameToVariable() throws IOException {
        FileInputStream fs = new FileInputStream(pathForShowSheet);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet sheet1 = wb.getSheetAt(0);

        numberOfRows = sheet1.getLastRowNum();
        String showName;
        String comicsSelected;
        String hostsSelected;

        for (int i = 0; i < numberOfRows; i++) {
            comicsSelected = String.valueOf(sheet1.getRow(i + 1).getCell(5));
            hostsSelected = String.valueOf(sheet1.getRow(i + 1).getCell(4));
            showName = String.valueOf(sheet1.getRow(i + 1).getCell(3));

            if (showName.contains("(T)")) {
                themedShows.add(showName);
                themedShowsMapWithSelectedComicsName.put(showName, comicsSelected);
            } else if (showName.contains("SLOT")) {
                continue;
            } else {
                nonThemedShows.add(showName);
                nonThemedShowsMapWithSelectedComicsName.put(showName, comicsSelected);
            }
            hostingShows.add(showName);
            hostingShowsMapWithSelectedComicsName.put(showName, hostsSelected);
        }

        FileOutputStream fos = new FileOutputStream(pathForShowSheet);
        wb.write(fos);
        fos.close();

    }

    public void sortingDetailsForNonThemedShows() {
        sortingDetailsForShows(nonThemedShows, nonThemedShowsMapWithSelectedComicsName, comicsMappedWithNonThemedShows);
    }

    public void sortingDetailsForThemedShows() {
        sortingDetailsForShows(themedShows, themedShowsMapWithSelectedComicsName, comicsMappedWithThemedShows);
    }

    public void sortingDetailsForHostingShows() {
        sortingDetailsForShows(hostingShows, hostingShowsMapWithSelectedComicsName, comicsMappedWithHostingShows);
    }


    public void sortingDetailsForShows(ArrayList<String> shows, Map<String, String> showsMapWithSelectedComicsName, Map<String, ArrayList<String>> comicsMappedWithShows) {
        for (String s : comicsName) {
            int count = 0;
            for (String show : shows) {
                if (showsMapWithSelectedComicsName.get(show) != null) {
                    if (showsMapWithSelectedComicsName.get(show).toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                        tempShowsName.add(count, show);
                        count++;
                    }
                }
            }
            comicsMappedWithShows.put(s, (ArrayList<String>) tempShowsName.clone());
            tempShowsName.removeAll(tempShowsName);
        }
    }

    public void collectingResponseForAllShows() throws IOException {
        System.out.println("---------------------------------------------------------------------------------------");
        String[] variableComicsName = comicsName;
        Map<String, ArrayList<String>> variableComicsMappedWithNonThemedShows = comicsMappedWithNonThemedShows;
        Map<String, ArrayList<String>> variableComicsMappedWithThemedShows = comicsMappedWithThemedShows;
        Map<String, ArrayList<String>> variableComicsMappedWithHostingShows = comicsMappedWithHostingShows;

        FileInputStream fs = new FileInputStream(pathForShowSheet);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet updatePerformerList = wb.createSheet("Updated Performer Details");
        updatePerformerList.createRow(0).createCell(0).setCellValue("S.No.");
        updatePerformerList.getRow(0).createCell(1).setCellValue("Comics Name");
        updatePerformerList.getRow(0).createCell(2).setCellValue("Assigned Non-themed Show");
        updatePerformerList.getRow(0).createCell(3).setCellValue("Sum of NTS");
        updatePerformerList.getRow(0).createCell(4).setCellValue("Assigned Themed Show");
        updatePerformerList.getRow(0).createCell(5).setCellValue("Sum of TS");
        updatePerformerList.getRow(0).createCell(6).setCellValue("Assigned Hosting");
        updatePerformerList.getRow(0).createCell(7).setCellValue("Sum of Hosting");
        updatePerformerList.getRow(0).createCell(8).setCellValue("Total Sum of Assigned Show");

        for (int i = 0; i < variableComicsName.length; i++) {

            int sizeOfThemedSpots = 0;
            int sizeOfNonThemedSpots = 0;
            int sizeOfHostingSpots = 0;

            if (variableComicsMappedWithNonThemedShows.get(variableComicsName[i]) != null)
                sizeOfNonThemedSpots = variableComicsMappedWithNonThemedShows.get(variableComicsName[i]).size();
            if (variableComicsMappedWithThemedShows.get(variableComicsName[i]) != null)
                sizeOfThemedSpots = variableComicsMappedWithThemedShows.get(variableComicsName[i]).size();
            if (variableComicsMappedWithHostingShows.get(variableComicsName[i]) != null)
                sizeOfHostingSpots = variableComicsMappedWithHostingShows.get(variableComicsName[i]).size();

            int totalSumOfSpots = sizeOfThemedSpots + sizeOfNonThemedSpots + sizeOfHostingSpots;
            String comicName = variableComicsName[i];
            String listOfNonThemedShows = String.valueOf(variableComicsMappedWithNonThemedShows.get(variableComicsName[i])).replace("[","").replace("]","");
            String listOfThemedShows = String.valueOf(variableComicsMappedWithThemedShows.get(variableComicsName[i])).replace("[","").replace("]","");
            String listOfHostingShows = String.valueOf(variableComicsMappedWithHostingShows.get(variableComicsName[i])).replace("[","").replace("]","");


            System.out.println("Spots assigned to *" + comicName + "* for Non themed shows are *" + listOfNonThemedShows + "* and *Sum is " + sizeOfNonThemedSpots + "*");
            System.out.println("Spots assigned to *" + comicName + "* for Themed shows are *" + listOfThemedShows + "* and *Sum is " + sizeOfThemedSpots + "*");
            System.out.println("Spots assigned to *" + comicName + "* for Hosting are *" + listOfHostingShows + "* and *Sum is " + sizeOfHostingSpots + "*");

            System.out.println("*Total Number of Spots* assigned to " + variableComicsName[i] + " are *" + totalSumOfSpots + "*");
            System.out.println("---------------------------------------------------------------------------------------");


            updatePerformerList.createRow(i + 1).createCell(0).setCellValue(i + 1);
            updatePerformerList.getRow(i + 1).createCell(1).setCellValue(comicName);
            updatePerformerList.getRow(i + 1).createCell(2).setCellValue(listOfNonThemedShows);
            updatePerformerList.getRow(i + 1).createCell(3).setCellValue(sizeOfNonThemedSpots);
            updatePerformerList.getRow(i + 1).createCell(4).setCellValue(listOfThemedShows);
            updatePerformerList.getRow(i + 1).createCell(5).setCellValue(sizeOfThemedSpots);
            updatePerformerList.getRow(i + 1).createCell(6).setCellValue(listOfHostingShows);
            updatePerformerList.getRow(i + 1).createCell(7).setCellValue(sizeOfHostingSpots);
            updatePerformerList.getRow(i + 1).createCell(8).setCellValue(totalSumOfSpots);

        }
        FileOutputStream fos = new FileOutputStream(pathForShowSheet);
        wb.write(fos);
        fos.close();

    }



}
