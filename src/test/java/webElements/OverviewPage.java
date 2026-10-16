package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Commons;

public class OverviewPage {

    public static final String PRODUCT_MANAGEMENT = "groupNode_product_management";
    public static final String PRODUCT_DEVELOPMENT = "itemNode_product_management_product_development_0";

    // ids changed after CMO
    public static final String TASK_LIST_END = ":_FOTsdi_pgNode_tasklist::icon";
    public static final String CREATE_ITEM_END = ":_FONSr2:0:_FOTRaT:0:RAtl1";
    public static final String CREATE_DOCUMENT_END = ":_FONSr2:0:_FOTRaT:0:RAtl4";
    public static final String CREATE_PART_END = ":_FONSr2:0:_FOTRaT:0:RAtl10";
    public static final String CREATE_MANUFACTURER_END = ":_FONSr2:0:_FOTRaT:0:RAtl14";
    public static final String CREATE_CHANGE_ORDER = "pt1:_FOr1:1:_FONSr2:0:_FOTRaT:0:RAtl6";
    public static final String CREATE_CHANGE_REQUEST_CHARLIE = "pt1:_FOr1:1:_FONSr2:0:_FOTRaT:0:RAtl6";
    public static final String CREATE_CHANGE_REQUEST_GAMMA = "pt1:_FOr1:1:_FONSr2:0:_FOTRaT:0:RAtl8";

    public static final String SEARCH_END = ":_FOTsdi_pgNode_quickSearch::icon";
    public static final String SEARCH_BOX_END = ":r13_sb1:r13_it2::content";
    public static final String SEARCH_TEXT_AREA_END = ":r13_21b_it1::content";
    public static final String SEARCH_DD_END = ":r13_soc1::content";
    public static final String SEARCH_RESULT_POPUP_END = ":r13_sb1:r13_it2::_fndSuggestPopup_sugg";
    public static final String SEARCH_ICON_END = ":r13_sb1:r13_cil1::icon";
    public static final String SEARCH_FIRST_RESULT_END = ":rstab:_ATp:table1:0:itmNoLnkForSearch";
    public static final String SEARCH_BUTTON_END = ":r13_21b_cb2";

    public static final String CLIPBOARD_END = ":_FOTsdi_pgNode_clipboard::icon";
    public static final String RECENT_ITEMS_END = ":1:sdi3::disAcr";
    public static final String RECENT_LIST_1_END = ":t3:0:cl2";
    public static final String RECENT_LIST_20_END = ":t3:19:cl2";
    public static final String RECENT_ITEMS_LIST_ITEMS_END = ":cl2";
    public static final String FAVORITES_ITEMS_LIST_ITEMS_END = ":cl1";

    //pop up
    public static final String CLASS_END = ":s2:inputComboboxListOfValues1::content";
    public static final String CLASS = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:inputComboboxListOfValues1::content";
    public static final String DD_FIRST_PRODUCT = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:inputComboboxListOfValues1::su0";
    public static final String DD_FIRST_PRODUCT_END = ":s2:inputComboboxListOfValues1::su0";
    public static final String PUOM_DD = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:1:primaryUomCodeDispId::lovIconId";
    public static final String PUOM_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:";
    public static final String PUOM_END = ":primaryUomCodeDispId::content";
    public static final String DD_FIRST_PUOM_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:";
    public static final String DD_FIRST_PUOM_END = ":primaryUomCodeDispId::su0";
    public static final String DESCRIPTION_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:";
    public static final String DESCRIPTION_END = ":it2::content";

    public static final String PGM_END = ":f1_selectPartGenerationMethodItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String PGM_DEV = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:1:f1_addVariantVersionToAnExistingFItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String EXISTING_FAMILY = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:0:f1_a2ExistingFamilyItemEffBaseEO2_ATTRIBUTE_CHAR2::content";
    public static final String EXISTING_FAMILY_END = "f1_a2ExistingFamilyItemEffBaseEO2_ATTRIBUTE_CHAR2::content";
    public static final String FAMILY_OPTION_END = ":f1_a2SpecifyFamilyOptionVariantItemEffBaseEO2_ATTRIBUTE_CHAR3::content";
    public static final String FAMILY_VERSION_END = ":f1_a2SpecifyFamilyVersionItemEffBaseEO2_ATTRIBUTE_CHAR4::content";
    public static final String LEGACY_END = ":f1_a3LegacyFamilyPartNumberItemEffBaseEO2_ATTRIBUTE_CHAR1::content";
//    public static final String LEGACY = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:1:f1_a3LegacyFamilyPartNumberItemEffBaseEO2_ATTRIBUTE_CHAR1::content";
//    public static final String DEV_LEGACY = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:r1:1:f1_partNumberItemEffBaseEO2_ATTRIBUTE_CHAR1::content";
    public static final String LOB_END_PN0 = ":f1_businessLineItemEffBaseEO1_ATTRIBUTE_CHAR2::content";
    public static final String LOB_END = ":f1_businessLineItemEffBaseEO3_ATTRIBUTE_CHAR2::content"; // id changed
    public static final String DA_END_PN0 = ":f1_designAuthorityItemEffBaseEO1_ATTRIBUTE_CHAR3::content";
    public static final String DA_END = ":f1_designAuthorityItemEffBaseEO3_ATTRIBUTE_CHAR3::content"; // id changed
    public static final String CC_END_PN0 = ":f1_customCotsItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String CC_END = ":f1_customCotsItemEffBaseEO3_ATTRIBUTE_CHAR1::content";// id changed
    //public static final String PART_NUMBER = ":f1_partNumberItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String PART_NUMBER_END = ":f1_partNumberItemEffBaseEO2_ATTRIBUTE_CHAR1::content";
    public static final String PACKAGING_TYPE_END = ":f1_PackTypeItemEffBaseEO4_ATTRIBUTE_CHAR6::content";
    public static final String PACKAGING_TYPE_1_END = ":f1_PackTypeItemEffBaseEO4_ATTRIBUTE_CHAR1::content";
    public static final String SAVE_AND_CLOSE = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:cb1";
    public static final String SAVE_AND_CLOSE_END = ":s2:cb1";
    public static final String CANCEL = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:s2:cb3";
    public static final String CANCEL_END = ":s2:cb3";

    public static final String DGM_END = "f1_selectDocumentGenerationMethodItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
//    public static final String PREFIX_END = ":f1_a2PrefixItemEffBaseEO2_ATTRIBUTE_CHAR1::content"; id changed                                         :f1_prefixItemEffBaseEO2_ATTRIBUTE_CHAR1::content
    public static final String PREFIX_END = ":f1_prefixItemEffBaseEO2_ATTRIBUTE_CHAR1::content";
    public static final String RPF_END = ":f1_a2RootPartFamilyItemEffBaseEO2_ATTRIBUTE_CHAR2::content";
    public static final String VERSION_END = ":f1_a2VersionItemEffBaseEO2_ATTRIBUTE_CHAR3::content";
    public static final String LEGACY_DOC_END = ":f1_a3LegacyDocumentNumberItemEffBaseEO2_ATTRIBUTE_CHAR4::content";

    public static final String MANUFACTURER_END = ":tradingPartnerIdId::content";
    public static final String MPN_END = ":it1::content";
    public static final String PART_SAVE_AND_CLOSE = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:cb1";
    public static final String PART_SAVE_AND_CLOSE_END = ":cb1";
    public static final String STATUS_END = ":soc1::content";
    public static final String MANUFACTURER_DD_END = ":tradingPartnerIdId::lovIconId";
    public static final String MANUFACTURER_DD_SEARCH_END = ":tradingPartnerIdId::dropdownPopup::popupsearch";
    public static final String MANUFACTURER_SEARCH_BOX_END = ":tradingPartnerIdId::_afrLovInternalQueryId:value00::content";
    public static final String MANUFACTURER_SEARCH_BOX_SEARCH_END = ":tradingPartnerIdId::_afrLovInternalQueryId::search";
    public static final String MANUFACTURER_FIRST_SEARCH_RESULT_END = ":tradingPartnerIdId_afrLovInternalTableId::db";
    public static final String MANUFACTURER_SEARCH_BOX_OK_END = ":tradingPartnerIdId::lovDialogId::ok";

    public static final String CHANGE_TYPE_END = ":s2:soc2::content";
    public static final String NAME_END = ":s2:r2:1:it3::content";
    public static final String DESC_END = ":it2::content";
    public static final String PRIORITY_END = ":s2:r2:1:soc2::content";
    public static final String SEVERITY_END = ":f1233_severityChangeEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String C_CODE_END = ":f1233_categoryCodeChangeEffBaseEO1_ATTRIBUTE_CHAR2::content";
    public static final String C_CODE_3_END = ":f1233_categoryCodeChangeEffBaseEO1_ATTRIBUTE_CHAR3::content";
    public static final String J_CODE_END = ":f1233_justificationCodeChangeEffBaseEO1_ATTRIBUTE_CHAR3::content";
    public static final String D_TYPE_END = ":f1233_deviationTypeChangeEffBaseEO1_ATTRIBUTE_CHAR4::content";
    public static final String CHANGE_SAVE_AND_EDIT_END = ":s2:cb6";
    // public static final String CHANGE_CANCEL_END = ":s2:cb8";

    public static final String EXPAND_MFI_END = ":AP1:arrowImage0";
    public static final String MFI_AREA_END = ":pb1::content";

    public static final String OVERDUE_DEADLINE_END = ":AP1:i3j_id_1:0:cl3";
    public static final String APPROACHING_DEADLINE_END = ":AP1:i3j_id_1:1:cl3";
    public static final String WAITING_FOR_REVIEW_END = ":AP1:i3j_id_1:2:cl3";
    public static final String WFR_TABLE_END = ":lv1::db";

    public static final String WFR_ELEMENT_ROW_END = ":pgl2";
    public static final String WFR_FIRST_ROW_END = ":li1";
    public static final String WFR_ELEMENT_LINK_OPEN_NEW_END = ":cl1";
    public static final String WFR_ELEMENT_LINK_OPEN_END = ":cil3::icon";
    public static final String WFR_ELEMENT_APPROVE_END = ":cb1";
    public static final String WFR_ELEMENT_REJECT_END = ":cb2";

    public static WebDriver driver = Commons.driver;

    public static void reachOverviewPage(){
        driver.navigate().refresh();
        driver.findElement(By.id(OracleHome.HOME)).click();
        Commons.sleepWait(3000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();
    }
}