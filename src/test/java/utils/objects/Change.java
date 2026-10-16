package utils.objects;

public class Change {

    public String type;
    public String subType;
    public String number;
    public String name;
    public String description;
    public String priority;
    public String severity;
    public String categoryCode;
    public String justificationCode;
    public String deviationType;

    public Change(){
        this("CO", "Standard Change Order", "NA", "NA", "Refer to Severity", "3 - Standard", "NPI Project", "Maintenance");
    }

    public Change(String type, String subType){
        this(type, subType, "NA", "NA", "Refer to Severity", "3 - Standard", "NPI Project", "Maintenance");
    }

    public Change(String type, String subType, String name, String description, String priority, String severity, String categoryCode, String justificationCode){
        this.type = type;
        this.subType = subType;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.severity = severity;
        this.categoryCode = categoryCode;
        this.justificationCode = justificationCode;
        this.deviationType = "Unplanned (Waiver)";
    }
}
