package StepDefinitions;

public class Constants extends GlobalFunctions implements ConstantDecider {

    public int totalNumberOfSpots = 80;
    public int numberOfNewComicSpots = 40;
    public int numberOfOldComicSpots = totalNumberOfSpots - numberOfNewComicSpots;
    public int totalNumberOfOpenMics = 7;
    public String pathForFormResponse = "C:\\Users\\HP\\OneDrive\\Desktop\\JCC " + path + ".xlsx";
    public String pathForJCCDatabase = "C:\\Users\\HP\\OneDrive\\Desktop\\JCC DB.xlsx";
    public String venue1 = "Bringer Mic, Monday 10:00 PM at JCC";
    public String venue2 = "Bringer Mic, Tuesday 8:00 PM at JCC";
    public String venue3 = "Bringer Mic, Tuesday 10:00 PM at JCC";
    public String venue4 = "Bringer Mic, Wednesday 8:00 PM at JCC";
    public String venue5 = "Bringer Mic, Wednesday 10:00 PM at JCC";
    public String venue6 = "Bringer Mic, Thursday 10:00 PM at JCC";
    public String venue7 = "Bringer Mic, Friday 10:00 PM at JCC";
    public String venue8 = "Non Bringer Mic, Saturday 8:00 PM at Hosteller";
    public String venue9 = "Bringer Mic, Saturday 10:00 PM at JCC";
    public String venue10 = "Non Bringer Mic, Sunday 7:00 PM at Friendly Beans, Mansarovar";
    public String venue11 = "Bringer Mic, Sunday 10:00 PM at JCC";
    public String[] venues = {venue1, venue2, venue3, venue4, venue5, venue6, venue7, venue8, venue9, venue10, venue11};

    public String sheetVenue1 = "Monday 10:00 PM at JCC";
    public String sheetVenue2 = "Tuesday 8:00 PM at JCC";
    public String sheetVenue3 = "Tuesday 10:00 PM at JCC";
    public String sheetVenue4 = "Wednesday 8:00 PM at JCC";
    public String sheetVenue5 = "Wednesday 10:00 PM at JCC";
    public String sheetVenue6 = "Thursday 10:00 PM at JCC";
    public String sheetVenue7 = "Friday 10:00 PM at JCC";
    public String sheetVenue8 = "Saturday 8:00 PM at Hosteller";
    public String sheetVenue9 = "Saturday 10:00 PM at JCC";
    public String sheetVenue10 = "Sunday 7:00 PM at Friendly Beans, Mansarovar";
    public String sheetVenue11 = "Sunday 10:00 PM at JCC";

    public String[] sheetVenue = {sheetVenue1, sheetVenue2, sheetVenue3, sheetVenue4, sheetVenue5, sheetVenue6, sheetVenue7
            , sheetVenue8, sheetVenue9, sheetVenue10, sheetVenue11};

}
