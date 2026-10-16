package utils.objects;

public class Doc extends Type{

    public Doc(){
        this("NPI Document", "NA", "NA", 0, "NA", "NA", "NA", "NA", "Air", "CZ52 - Brno", "COTS","NA");
    }

    public Doc(String className, String name, String description, int docGenMtd,
               String prefix, String rootPartFamily, String version, String legacyPartNumber,
               String lineOfBusiness, String designAuthority, String customCOTS,String specialCase){
        this.className = className;
        this.name = name;
        this.description = description;
        this.docGenMtd = docGenMtd;
        this.prefix = prefix;
        this.rootPartFamily = rootPartFamily;
        this.version = version;
        this.legacyPartNumber = legacyPartNumber;
        this.lineOfBusiness = lineOfBusiness;
        this.designAuthority = designAuthority;
        this.customCOTS = customCOTS;
        this.specialCase = specialCase;
        this.type = "Documents";
    }

    public Doc(String className, String name, String description, int docGenMtd,
               String prefix, String rootPartFamily, String version, String legacyPartNumber,
               String specialCase){
        this.className = className;
        this.name = name;
        this.description = description;
        this.docGenMtd = docGenMtd;
        this.prefix = prefix;
        this.rootPartFamily = rootPartFamily;
        this.version = version;
        this.legacyPartNumber = legacyPartNumber;
        this.specialCase = specialCase;
        this.type = "Documents";
    }

}