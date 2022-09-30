package StepDefinitions;

interface ConstantDecider {
    //    String sheetName = "openMicSorting";
//    String sheetName = "nonThemedShowName";
//    String sheetName = "themedShowName";
    String sheetName = "hostingShowName";

    String path = sheetName.equalsIgnoreCase("openMicSorting") ? "Form" : "Spot";

}
