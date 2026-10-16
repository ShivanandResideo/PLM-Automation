package utils.objects;

import utils.Commons;

public class Item extends Type{

    // the below constructor create an object for only IC Programmed item type
   public Item(){
      this("IC Programmed", "NA", "NA", "EACH [EA]", 1, "NA", "NA", "NA", "NA", "Air", "CZ52 - Brno", "COTS", "NA");
   }

   public Item(String className, String name, String description, String PUOM, int partGenMtd,
               String existingFamily, String familyOption, String familyVersion, String legacyPartNumber,
               String lineOfBusiness, String designAuthority, String customCOTS, String specialCase){

      this.className = className;
      this.name = name;
      this.description = description;
      this.PUOM = PUOM;
      this.partGenMtd = partGenMtd;
      this.existingFamily = existingFamily;
      this.familyOption = familyOption;
      this.familyVersion = familyVersion;
      this.legacyPartNumber = legacyPartNumber;
      this.lineOfBusiness = lineOfBusiness;
      this.designAuthority = designAuthority;
      this.customCOTS = customCOTS;
      this.specialCase = specialCase;
      this.type = "Items";
   }

   public int setPGM(String part){
      return Integer.parseInt(Commons.readFromExcel("EditItemPage", "GeneralInformation", part, "row").get(2));
   }
}
