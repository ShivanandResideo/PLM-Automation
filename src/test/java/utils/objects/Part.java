package utils.objects;

import utils.Commons;

public class Part {

    public String MPN;
    public String manufacturer;
    public String status;
    public String description;
    public String specialCase;

    public Part(){
        this(Commons.userRandomString("Part"), "AUTOMATION COMPONENTS INC", "Active", "NA", "NA");
    }

    public Part(String MPN, String manufacturer, String status, String description, String specialCase){
        if(MPN.equalsIgnoreCase("NA"))
            this.MPN = Commons.userRandomString("Part");
        else
            this.MPN = MPN;

        this.manufacturer = manufacturer;
        this.status = status;

        if(specialCase.equalsIgnoreCase("Description Empty"))
            this.description = "";
        else
            this.description = Commons.userRandomString("Description");

        this.specialCase = specialCase;
    }
}
