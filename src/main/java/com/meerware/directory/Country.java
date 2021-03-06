package com.meerware.directory;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

/**
 * Enumeration of countries. This has their 2 character code, common name and aliases.
 */
enum Country {

    /**
     * Aruba.
     */
    ARUBA("AW", "Aruba", "ABW", "ARU"),

    /**
     * Afghanistan.
     */
    AFGHANISTAN("AF", "Afghanistan", "Islamic Republic of Afghanistan", "Afġānistān", "AFG"),

    /**
     * Angola.
     */
    ANGOLA("AO", "Angola", "Republic of Angola", "República de Angola", "ʁɛpublika de an'ɡɔla", "AGO", "ANG"),

    /**
     * Anguilla.
     */
    ANGUILLA("AI", "Anguilla", "AIA"),

    /**
     * Åland Islands.
     */
    ALAND_ISLANDS("AX", "Åland Islands", "Aaland", "Aland", "Ahvenanmaa", "ALA"),

    /**
     * Albania.
     */
    ALBANIA("AL", "Albania", "Republic of Albania", "Shqipëri", "Shqipëria", "Shqipnia", "ALB"),

    /**
     * Andorra.
     */
    ANDORRA("AD", "Andorra", "Principality of Andorra", "Principat d'Andorra", "AND"),

    /**
     * United Arab Emirates.
     */
    UNITED_ARAB_EMIRATES("AE", "United Arab Emirates", "UAE", "Emirates", "ARE"),

    /**
     * Argentina.
     */
    ARGENTINA("AR", "Argentina", "Argentine Republic", "República Argentina", "ARG"),

    /**
     * Armenia.
     */
    ARMENIA("AM", "Armenia", "Republic of Armenia", "Hayastan", "Հայաստանի Հանրապետություն", "ARM"),

    /**
     * American Samoa.
     */
    AMERICAN_SAMOA("AS", "American Samoa", "Amerika Sāmoa", "Amelika Sāmoa", "Sāmoa Amelika", "ASM", "ASA"),

    /**
     * Antarctica.
     */
    ANTARCTICA("AQ", "Antarctica", "ATA"),

    /**
     * French Southern and Antarctic Lands.
     */
    FRENCH_SOUTHERN_AND_ANTARCTIC_LANDS("TF", "Territory of the French Southern and Antarctic Lands",
            "French Southern Territories", "ATF"),

    /**
     * Antigua and Barbuda.
     */
    ANTIGUA_AND_BARBUDA("AG", "Antigua and Barbuda", "ATG", "ANT"),

    /**
     * Australia.
     */
    AUSTRALIA("AU", "Australia", "Commonwealth of Australia", "AUS"),

    /**
     * Austria.
     */
    AUSTRIA("AT", "Austria", "Republic of Austria", "Osterreich", "Oesterreich", "AUT"),

    /**
     * Azerbaijan.
     */
    AZERBAIJAN("AZ", "Azerbaijan", "Republic of Azerbaijan", "Azərbaycan Respublikası", "AZE"),

    /**
     * Burundi.
     */
    BURUNDI("BI", "Burundi", "Republic of Burundi", "Republika y'Uburundi", "République du Burundi", "BDI"),

    /**
     * Belgium.
     */
    BELGIUM("BE", "Belgium", "Kingdom of Belgium", "België", "Belgie", "Belgien", "Belgique", "Koninkrijk België",
            "Royaume de Belgique", "Königreich Belgien", "BEL"),

    /**
     * Benin.
     */
    BENIN("BJ", "Benin", "Republic of Benin", "République du Bénin", "BEN"),

    /**
     * Burkina Faso.
     */
    BURKINA_FASO("BF", "Burkina Faso", "BFA", "BUR"),

    /**
     * Bangladesh.
     */
    BANGLADESH("BD", "Bangladesh", "People's Republic of Bangladesh", "Gônôprôjatôntri Bangladesh", "BGD", "BAN"),

    /**
     * Bulgaria.
     */
    BULGARIA("BG", "Bulgaria", "Republic of Bulgaria", "Република България", "BGR", "BUL"),

    /**
     * Bahrain.
     */
    BAHRAIN("BH", "Bahrain", "Kingdom of Bahrain", "Mamlakat al-Baḥrayn", "BHR", "BRN"),

    /**
     * Bahamas.
     */
    BAHAMAS("BS", "Bahamas", "Commonwealth of the Bahamas", "BHS", "BAH"),

    /**
     * Bosnia and Herzegovina.
     */
    BOSNIA_AND_HERZEGOVINA("BA", "Bosnia and Herzegovina", "Bosnia-Herzegovina", "Босна и Херцеговина", "BIH"),

    /**
     * Saint Barthélemy.
     */
    SAINT_BARTHELEMY("BL", "Saint Barthélemy", "Collectivity of Saint Barthélemy", "St. Barthelemy",
            "Collectivité de Saint-Barthélemy", "BLM"),

    /**
     * Belarus.
     */
    BELARUS("BY", "Belarus", "Republic of Belarus", "Bielaruś", "Белоруссия", "Республика Белоруссия", "BLR"),

    /**
     * Belize.
     */
    BELIZE("BZ", "Belize", "BLZ", "BIZ"),

    /**
     * Bermuda.
     */
    BERMUDA("BM", "Bermuda", "The Islands of Bermuda", "The Bermudas", "Somers Isles", "BMU", "BER"),

    /**
     * Bolivia.
     */
    BOLIVIA("BO", "Bolivia", "Plurinational State of Bolivia", "Buliwya", "Wuliwya", "Bolivia, Plurinational State of",
            "Estado Plurinacional de Bolivia", "Buliwya Mamallaqta", "Wuliwya Suyu", "Tetã Volívia", "BOL"),

    /**
     * Brazil.
     */
    BRAZIL("BR", "Brazil", "Federative Republic of Brazil", "Brasil", "República Federativa do Brasil", "BRA"),

    /**
     * Barbados.
     */
    BARBADOS("BB", "Barbados", "BRB", "BAR"),

    /**
     * Brunei.
     */
    BRUNEI("BN", "Brunei", "Nation of Brunei, Abode of Peace", "Brunei Darussalam", "Nation of Brunei",
            "the Abode of Peace", "BRN", "BRU"),

    /**
     * Bhutan.
     */
    BHUTAN("BT", "Bhutan", "Kingdom of Bhutan", "BTN", "BHU"),

    /**
     * Bouvet Island.
     */
    BOUVET_ISLAND("BV", "Bouvet Island", "Bouvetøya", "Bouvet-øya", "BVT"),

    /**
     * Botswana.
     */
    BOTSWANA("BW", "Botswana", "Republic of Botswana", "Lefatshe la Botswana", "BWA", "BOT"),

    /**
     * Central African Republic.
     */
    CENTRAL_AFRICAN_REPUBLIC("CF", "Central African Republic", "République centrafricaine", "CAF"),

    /**
     * Canada.
     */
    CANADA("CA", "Canada", "CAN"),

    /**
     * Cocos (Keeling) Islands.
     */
    COCOS_ KEELING _ISLANDS("CC", "Territory of the Cocos (Keeling) Islands", "Keeling Islands", "Cocos Islands",
            "CCK"),

    /**
     * Switzerland.
     */
    SWITZERLAND("CH", "Switzerland", "Swiss Confederation", "Schweiz", "Suisse", "Svizzera", "Svizra", "CHE", "SUI"),

    /**
     * Chile.
     */
    CHILE("CL", "Chile", "Republic of Chile", "República de Chile", "CHL", "CHI"),

    /**
     * China.
     */
    CHINA("CN", "China", "People's Republic of China", "Zhōngguó", "Zhongguo", "Zhonghua", "中华人民共和国",
            "Zhōnghuá Rénmín Gònghéguó", "CHN"),

    /**
     * Ivory Coast.
     */
    IVORY_COAST("CI", "Ivory Coast", "Republic of Côte d'Ivoire", "Côte d'Ivoire", "République de Côte d'Ivoire",
            "CIV"),

    /**
     * Cameroon.
     */
    CAMEROON("CM", "Cameroon", "Republic of Cameroon", "République du Cameroun", "CMR"),

    /**
     * DR Congo.
     */
    DR_CONGO("CD", "Democratic Republic of the Congo", "DR Congo", "Congo-Kinshasa",
            "Congo, the Democratic Republic of the", "DRC", "COD"),

    /**
     * Republic of the Congo.
     */
    REPUBLIC_OF_THE_CONGO("CG", "Republic of the Congo", "Congo", "Congo-Brazzaville", "COG", "CGO"),

    /**
     * Cook Islands.
     */
    COOK_ISLANDS("CK", "Cook Islands", "Kūki 'Āirani", "COK"),

    /**
     * Colombia.
     */
    COLOMBIA("CO", "Colombia", "Republic of Colombia", "República de Colombia", "COL"),

    /**
     * Comoros.
     */
    COMOROS("KM", "Comoros", "Union of the Comoros", "Union des Comores", "Udzima wa Komori", "al-Ittiḥād al-Qumurī",
            "COM"),

    /**
     * Cape Verde.
     */
    CAPE_VERDE("CV", "Cabo Verde", "Republic of Cabo Verde", "República de Cabo Verde", "CPV"),

    /**
     * Costa Rica.
     */
    COSTA_RICA("CR", "Costa Rica", "Republic of Costa Rica", "República de Costa Rica", "CRI", "CRC"),

    /**
     * Cuba.
     */
    CUBA("CU", "Cuba", "Republic of Cuba", "República de Cuba", "CUB"),

    /**
     * Curaçao.
     */
    CURAÇAO("CW", "Curaçao", "Country of Curaçao", "Curacao", "Kòrsou", "Land Curaçao", "Pais Kòrsou", "CUW"),

    /**
     * Christmas Island.
     */
    CHRISTMAS_ISLAND("CX", "Christmas Island", "Territory of Christmas Island", "CXR"),

    /**
     * Cayman Islands.
     */
    CAYMAN_ISLANDS("KY", "Cayman Islands", "CYM", "CAY"),

    /**
     * Cyprus.
     */
    CYPRUS("CY", "Cyprus", "Republic of Cyprus", "Kýpros", "Kıbrıs", "Κυπριακή Δημοκρατία", "Kıbrıs Cumhuriyeti",
            "CYP"),

    /**
     * Czechia.
     */
    CZECHIA("CZ", "Czech Republic", "Česká republika", "Česko", "CZE"),

    /**
     * Germany.
     */
    GERMANY("DE", "Germany", "Federal Republic of Germany", "Bundesrepublik Deutschland", "DEU", "GER"),

    /**
     * Djibouti.
     */
    DJIBOUTI("DJ", "Djibouti", "Republic of Djibouti", "Jabuuti", "Gabuuti", "République de Djibouti",
            "Gabuutih Ummuuno", "Jamhuuriyadda Jabuuti", "DJI"),

    /**
     * Dominica.
     */
    DOMINICA("DM", "Dominica", "Commonwealth of Dominica", "Dominique", "Wai‘tu kubuli", "DMA"),

    /**
     * Denmark.
     */
    DENMARK("DK", "Denmark", "Kingdom of Denmark", "Danmark", "Kongeriget Danmark", "DNK", "DEN"),

    /**
     * Dominican Republic.
     */
    DOMINICAN_REPUBLIC("DO", "Dominican Republic", "DOM"),

    /**
     * Algeria.
     */
    ALGERIA("DZ", "Algeria", "People's Democratic Republic of Algeria", "Dzayer", "Algérie", "DZA", "ALG"),

    /**
     * Ecuador.
     */
    ECUADOR("EC", "Ecuador", "Republic of Ecuador", "República del Ecuador", "ECU"),

    /**
     * Egypt.
     */
    EGYPT("EG", "Egypt", "Arab Republic of Egypt", "EGY"),

    /**
     * Eritrea.
     */
    ERITREA("ER", "Eritrea", "State of Eritrea", "ሃገረ ኤርትራ", "Dawlat Iritriyá", "ʾErtrā", "Iritriyā", "ERI"),

    /**
     * Western Sahara.
     */
    WESTERN_SAHARA("EH", "Sahrawi Arab Democratic Republic", "Taneẓroft Tutrimt", "ESH"),

    /**
     * Spain.
     */
    SPAIN("ES", "Spain", "Kingdom of Spain", "Reino de España", "ESP"),

    /**
     * Estonia.
     */
    ESTONIA("EE", "Estonia", "Republic of Estonia", "Eesti", "Eesti Vabariik", "EST"),

    /**
     * Ethiopia.
     */
    ETHIOPIA("ET", "Ethiopia", "Federal Democratic Republic of Ethiopia", "ʾĪtyōṗṗyā", "የኢትዮጵያ ፌዴራላዊ ዲሞክራሲያዊ ሪፐብሊክ",
            "ETH"),

    /**
     * Finland.
     */
    FINLAND("FI", "Finland", "Republic of Finland", "Suomi", "Suomen tasavalta", "Republiken Finland", "FIN"),

    /**
     * Fiji.
     */
    FIJI("FJ", "Fiji", "Republic of Fiji", "Viti", "Matanitu ko Viti", "Fijī Gaṇarājya", "FJI", "FIJ"),

    /**
     * Falkland Islands.
     */
    FALKLAND_ISLANDS("FK", "Falkland Islands", "Islas Malvinas", "Falkland Islands (Malvinas)", "FLK"),

    /**
     * France.
     */
    FRANCE("FR", "France", "French Republic", "République française", "FRA"),

    /**
     * Faroe Islands.
     */
    FAROE_ISLANDS("FO", "Faroe Islands", "Føroyar", "Færøerne", "FRO"),

    /**
     * Micronesia.
     */
    MICRONESIA("FM", "Micronesia", "Federated States of Micronesia", "Micronesia, Federated States of", "FSM"),

    /**
     * Gabon.
     */
    GABON("GA", "Gabon", "Gabonese Republic", "République Gabonaise", "GAB"),

    /**
     * United Kingdom.
     */
    UNITED_KINGDOM("GB", "United Kingdom", "United Kingdom of Great Britain and Northern Ireland", "UK",
            "Great Britain", "GBR"),

    /**
     * Georgia.
     */
    GEORGIA("GE", "Georgia", "Sakartvelo", "GEO"),

    /**
     * Guernsey.
     */
    GUERNSEY("GG", "Guernsey", "Bailiwick of Guernsey", "Bailliage de Guernesey", "GGY"),

    /**
     * Ghana.
     */
    GHANA("GH", "Ghana", "Republic of Ghana", "GHA"),

    /**
     * Gibraltar.
     */
    GIBRALTAR("GI", "Gibraltar", "GIB"),

    /**
     * Guinea.
     */
    GUINEA("GN", "Guinea", "Republic of Guinea", "République de Guinée", "GIN", "GUI"),

    /**
     * Guadeloupe.
     */
    GUADELOUPE("GP", "Guadeloupe", "Gwadloup", "GLP"),

    /**
     * Gambia.
     */
    GAMBIA("GM", "Gambia", "Republic of the Gambia", "GMB", "GAM"),

    /**
     * Guinea-Bissau.
     */
    GUINEA_BISSAU("GW", "Guinea-Bissau", "Republic of Guinea-Bissau", "República da Guiné-Bissau", "GNB", "GBS"),

    /**
     * Equatorial Guinea.
     */
    EQUATORIAL_GUINEA("GQ", "Equatorial Guinea", "Republic of Equatorial Guinea", "República de Guinea Ecuatorial",
            "République de Guinée équatoriale", "República da Guiné Equatorial", "GNQ", "GEQ"),

    /**
     * Greece.
     */
    GREECE("GR", "Greece", "Hellenic Republic", "Elláda", "Ελληνική Δημοκρατία", "GRC", "GRE"),

    /**
     * Grenada.
     */
    GRENADA("GD", "Grenada", "GRD", "GRN"),

    /**
     * Greenland.
     */
    GREENLAND("GL", "Greenland", "Grønland", "GRL"),

    /**
     * Guatemala.
     */
    GUATEMALA("GT", "Guatemala", "Republic of Guatemala", "GTM", "GUA"),

    /**
     * French Guiana.
     */
    FRENCH_GUIANA("GF", "French Guiana", "Guiana", "Guyane", "GUF"),

    /**
     * Guam.
     */
    GUAM("GU", "Guam", "Guåhån", "GUM"),

    /**
     * Guyana.
     */
    GUYANA("GY", "Guyana", "Co-operative Republic of Guyana", "GUY"),

    /**
     * Hong Kong.
     */
    HONG_KONG("HK", "Hong Kong", "Hong Kong Special Administrative Region of the People's Republic of China", "HKG"),

    /**
     * Heard Island and McDonald Islands.
     */
    HEARD_ISLAND_AND_MCDONALD_ISLANDS("HM", "Heard Island and McDonald Islands", "HMD"),

    /**
     * Honduras.
     */
    HONDURAS("HN", "Honduras", "Republic of Honduras", "República de Honduras", "HND", "HON"),

    /**
     * Croatia.
     */
    CROATIA("HR", "Croatia", "Republic of Croatia", "Hrvatska", "Republika Hrvatska", "HRV", "CRO"),

    /**
     * Haiti.
     */
    HAITI("HT", "Haiti", "Republic of Haiti", "République d'Haïti", "Repiblik Ayiti", "HTI", "HAI"),

    /**
     * Hungary.
     */
    HUNGARY("HU", "Hungary", "HUN"),

    /**
     * Indonesia.
     */
    INDONESIA("ID", "Indonesia", "Republic of Indonesia", "Republik Indonesia", "IDN", "INA"),

    /**
     * Isle of Man.
     */
    ISLE_OF_MAN("IM", "Isle of Man", "Ellan Vannin", "Mann", "Mannin", "IMN"),

    /**
     * India.
     */
    INDIA("IN", "India", "Republic of India", "Bhārat", "Bharat Ganrajya", "இந்தியா", "IND"),

    /**
     * British Indian Ocean Territory.
     */
    BRITISH_INDIAN_OCEAN_TERRITORY("IO", "British Indian Ocean Territory", "IOT"),

    /**
     * Ireland.
     */
    IRELAND("IE", "Ireland", "Republic of Ireland", "Éire", "Poblacht na hÉireann", "IRL"),

    /**
     * Iran.
     */
    IRAN("IR", "Iran", "Islamic Republic of Iran", "Iran, Islamic Republic of", "Jomhuri-ye Eslāmi-ye Irān", "IRN",
            "IRI"),

    /**
     * Iraq.
     */
    IRAQ("IQ", "Iraq", "Republic of Iraq", "Jumhūriyyat al-‘Irāq", "IRQ"),

    /**
     * Iceland.
     */
    ICELAND("IS", "Iceland", "Island", "Republic of Iceland", "Lýðveldið Ísland", "ISL"),

    /**
     * Israel.
     */
    ISRAEL("IL", "Israel", "State of Israel", "Medīnat Yisrā'el", "ISR"),

    /**
     * Italy.
     */
    ITALY("IT", "Italy", "Italian Republic", "Repubblica italiana", "ITA"),

    /**
     * Jamaica.
     */
    JAMAICA("JM", "Jamaica", "JAM"),

    /**
     * Jersey.
     */
    JERSEY("JE", "Jersey", "Bailiwick of Jersey", "Bailliage de Jersey", "Bailliage dé Jèrri", "JEY"),

    /**
     * Jordan.
     */
    JORDAN("JO", "Jordan", "Hashemite Kingdom of Jordan", "al-Mamlakah al-Urdunīyah al-Hāshimīyah", "JOR"),

    /**
     * Japan.
     */
    JAPAN("JP", "Japan", "Nippon", "Nihon", "JPN"),

    /**
     * Kazakhstan.
     */
    KAZAKHSTAN("KZ", "Kazakhstan", "Republic of Kazakhstan", "Qazaqstan", "Казахстан", "Қазақстан Республикасы",
            "Qazaqstan Respublïkası", "Республика Казахстан", "Respublika Kazakhstan", "KAZ"),

    /**
     * Kenya.
     */
    KENYA("KE", "Kenya", "Republic of Kenya", "Jamhuri ya Kenya", "KEN"),

    /**
     * Kyrgyzstan.
     */
    KYRGYZSTAN("KG", "Kyrgyzstan", "Kyrgyz Republic", "Киргизия", "Кыргыз Республикасы", "Kyrgyz Respublikasy", "KGZ"),

    /**
     * Cambodia.
     */
    CAMBODIA("KH", "Cambodia", "Kingdom of Cambodia", "KHM", "CAM"),

    /**
     * Kiribati.
     */
    KIRIBATI("KI", "Kiribati", "Independent and Sovereign Republic of Kiribati", "Republic of Kiribati",
            "Ribaberiki Kiribati", "KIR"),

    /**
     * Saint Kitts and Nevis.
     */
    SAINT_KITTS_AND_NEVIS("KN", "Saint Christopher and Nevis", "Federation of Saint Christopher and Nevisa",
            "Federation of Saint Christopher and Nevis", "KNA", "SKN"),

    /**
     * South Korea.
     */
    SOUTH_KOREA("KR", "South Korea", "Republic of Korea", "Korea, Republic of", "KOR"),

    /**
     * Kosovo.
     */
    KOSOVO("XK", "Kosovo", "Republic of Kosovo", "Република Косово", "UNK", "KOS"),

    /**
     * Kuwait.
     */
    KUWAIT("KW", "Kuwait", "State of Kuwait", "Dawlat al-Kuwait", "KWT", "KUW"),

    /**
     * Laos.
     */
    LAOS("LA", "Laos", "Lao People's Democratic Republic", "Lao", "Sathalanalat Paxathipatai Paxaxon Lao", "LAO"),

    /**
     * Lebanon.
     */
    LEBANON("LB", "Lebanon", "Lebanese Republic", "Al-Jumhūrīyah Al-Libnānīyah", "LBN", "LIB"),

    /**
     * Liberia.
     */
    LIBERIA("LR", "Liberia", "Republic of Liberia", "LBR"),

    /**
     * Libya.
     */
    LIBYA("LY", "Libya", "State of Libya", "Dawlat Libya", "LBY", "LBA"),

    /**
     * Saint Lucia.
     */
    SAINT_LUCIA("LC", "Saint Lucia", "LCA"),

    /**
     * Liechtenstein.
     */
    LIECHTENSTEIN("LI", "Liechtenstein", "Principality of Liechtenstein", "Fürstentum Liechtenstein", "LIE"),

    /**
     * Sri Lanka.
     */
    SRI_LANKA("LK", "Sri Lanka", "Democratic Socialist Republic of Sri Lanka", "ilaṅkai", "LKA", "SRI"),

    /**
     * Lesotho.
     */
    LESOTHO("LS", "Lesotho", "Kingdom of Lesotho", "Muso oa Lesotho", "LSO", "LES"),

    /**
     * Lithuania.
     */
    LITHUANIA("LT", "Lithuania", "Republic of Lithuania", "Lietuvos Respublika", "LTU"),

    /**
     * Luxembourg.
     */
    LUXEMBOURG("LU", "Luxembourg", "Grand Duchy of Luxembourg", "Grand-Duché de Luxembourg", "Großherzogtum Luxemburg",
            "Groussherzogtum Lëtzebuerg", "LUX"),

    /**
     * Latvia.
     */
    LATVIA("LV", "Latvia", "Republic of Latvia", "Latvijas Republika", "LVA", "LAT"),

    /**
     * Macau.
     */
    MACAU("MO", "Macau", "Macao Special Administrative Region of the People's Republic of China", "澳门", "Macao",
            "中華人民共和國澳門特別行政區", "Região Administrativa Especial de Macau da República Popular da China", "MAC"),

    /**
     * Saint Martin.
     */
    SAINT_MARTIN("MF", "Saint Martin", "Collectivity of Saint Martin", "Collectivité de Saint-Martin",
            "Saint Martin (French part)", "MAF"),

    /**
     * Morocco.
     */
    MOROCCO("MA", "Morocco", "Kingdom of Morocco", "Al-Mamlakah al-Maġribiyah", "MAR"),

    /**
     * Monaco.
     */
    MONACO("MC", "Monaco", "Principality of Monaco", "Principauté de Monaco", "MCO", "MON"),

    /**
     * Moldova.
     */
    MOLDOVA("MD", "Moldova", "Republic of Moldova", "Moldova, Republic of", "Republica Moldova", "MDA"),

    /**
     * Madagascar.
     */
    MADAGASCAR("MG", "Madagascar", "Republic of Madagascar", "Repoblikan'i Madagasikara", "République de Madagascar",
            "MDG", "MAD"),

    /**
     * Maldives.
     */
    MALDIVES("MV", "Maldives", "Republic of the Maldives", "Maldive Islands", "Dhivehi Raajjeyge Jumhooriyya", "MDV"),

    /**
     * Mexico.
     */
    MEXICO("MX", "Mexico", "United Mexican States", "Mexicanos", "Estados Unidos Mexicanos", "MEX"),

    /**
     * Marshall Islands.
     */
    MARSHALL_ISLANDS("MH", "Marshall Islands", "Republic of the Marshall Islands", "Aolepān Aorōkin M̧ajeļ", "MHL"),

    /**
     * Macedonia.
     */
    MACEDONIA("MK", "Macedonia", "Republic of Macedonia", "Macedonia, the Former Yugoslav Republic of",
            "Република Македонија", "MKD"),

    /**
     * Mali.
     */
    MALI("ML", "Mali", "Republic of Mali", "République du Mali", "MLI"),

    /**
     * Malta.
     */
    MALTA("MT", "Malta", "Republic of Malta", "Repubblika ta' Malta", "MLT"),

    /**
     * Myanmar.
     */
    MYANMAR("MM", "Myanmar", "Republic of the Union of Myanmar", "Burma", "Pyidaunzu Thanmăda Myăma Nainngandaw", "MMR",
            "MYA"),

    /**
     * Montenegro.
     */
    MONTENEGRO("ME", "Montenegro", "Crna Gora", "MNE"),

    /**
     * Mongolia.
     */
    MONGOLIA("MN", "Mongolia", "MNG", "MGL"),

    /**
     * Northern Mariana Islands.
     */
    NORTHERN_MARIANA_ISLANDS("MP", "Northern Mariana Islands", "Commonwealth of the Northern Mariana Islands",
            "Sankattan Siha Na Islas Mariånas", "MNP"),

    /**
     * Mozambique.
     */
    MOZAMBIQUE("MZ", "Mozambique", "Republic of Mozambique", "República de Moçambique", "MOZ"),

    /**
     * Mauritania.
     */
    MAURITANIA("MR", "Mauritania", "Islamic Republic of Mauritania", "al-Jumhūriyyah al-ʾIslāmiyyah al-Mūrītāniyyah",
            "MRT", "MTN"),

    /**
     * Montserrat.
     */
    MONTSERRAT("MS", "Montserrat", "MSR"),

    /**
     * Martinique.
     */
    MARTINIQUE("MQ", "Martinique", "MTQ"),

    /**
     * Mauritius.
     */
    MAURITIUS("MU", "Mauritius", "Republic of Mauritius", "République de Maurice", "MUS", "MRI"),

    /**
     * Malawi.
     */
    MALAWI("MW", "Malawi", "Republic of Malawi", "MWI", "MAW"),

    /**
     * Malaysia.
     */
    MALAYSIA("MY", "Malaysia", "MYS", "MAS"),

    /**
     * Mayotte.
     */
    MAYOTTE("YT", "Mayotte", "Department of Mayotte", "Département de Mayotte", "MYT"),

    /**
     * Namibia.
     */
    NAMIBIA("NA", "Namibia", "Republic of Namibia", "Namibië", "NAM"),

    /**
     * New Caledonia.
     */
    NEW_CALEDONIA("NC", "New Caledonia", "NCL"),

    /**
     * Niger.
     */
    NIGER("NE", "Niger", "Republic of Niger", "Nijar", "NER", "NIG"),

    /**
     * Norfolk Island.
     */
    NORFOLK_ISLAND("NF", "Norfolk Island", "Territory of Norfolk Island", "Teratri of Norf'k Ailen", "NFK"),

    /**
     * Nigeria.
     */
    NIGERIA("NG", "Nigeria", "Federal Republic of Nigeria", "Nijeriya", "Naíjíríà", "NGA", "NGR"),

    /**
     * Nicaragua.
     */
    NICARAGUA("NI", "Nicaragua", "Republic of Nicaragua", "República de Nicaragua", "NIC", "NCA"),

    /**
     * Niue.
     */
    NIUE("NU", "Niue", "NIU"),

    /**
     * Netherlands.
     */
    NETHERLANDS("NL", "Netherlands", "Holland", "Nederland", "The Netherlands", "NLD", "NED"),

    /**
     * Norway.
     */
    NORWAY("NO", "Norway", "Kingdom of Norway", "Norge", "Noreg", "Kongeriket Norge", "Kongeriket Noreg", "NOR"),

    /**
     * Nepal.
     */
    NEPAL("NP", "Nepal", "Federal Democratic Republic of Nepal", "Loktāntrik Ganatantra Nepāl", "NPL", "NEP"),

    /**
     * Nauru.
     */
    NAURU("NR", "Nauru", "Republic of Nauru", "Naoero", "Pleasant Island", "Ripublik Naoero", "NRU"),

    /**
     * New Zealand.
     */
    NEW_ZEALAND("NZ", "New Zealand", "Aotearoa", "NZL"),

    /**
     * Oman.
     */
    OMAN("OM", "Oman", "Sultanate of Oman", "Salṭanat ʻUmān", "OMN", "OMA"),

    /**
     * Pakistan.
     */
    PAKISTAN("PK", "Pakistan", "Islamic Republic of Pakistan", "Pākistān", "Islāmī Jumhūriya'eh Pākistān", "PAK"),

    /**
     * Panama.
     */
    PANAMA("PA", "Panama", "Republic of Panama", "República de Panamá", "PAN"),

    /**
     * Pitcairn Islands.
     */
    PITCAIRN_ISLANDS("PN", "Pitcairn Islands", "Pitcairn Group of Islands", "Pitcairn",
            "Pitcairn Henderson Ducie and Oeno Islands", "PCN"),

    /**
     * Peru.
     */
    PERU("PE", "Peru", "Republic of Peru", "República del Perú", "PER"),

    /**
     * Philippines.
     */
    PHILIPPINES("PH", "Philippines", "Republic of the Philippines", "Repúblika ng Pilipinas", "PHL", "PHI"),

    /**
     * Palau.
     */
    PALAU("PW", "Palau", "Republic of Palau", "Beluu er a Belau", "PLW"),

    /**
     * Papua New Guinea.
     */
    PAPUA_NEW_GUINEA("PG", "Papua New Guinea", "Independent State of Papua New Guinea",
            "Independen Stet bilong Papua Niugini", "PNG"),

    /**
     * Poland.
     */
    POLAND("PL", "Poland", "Republic of Poland", "Rzeczpospolita Polska", "POL"),

    /**
     * Puerto Rico.
     */
    PUERTO_RICO("PR", "Puerto Rico", "Commonwealth of Puerto Rico", "Estado Libre Asociado de Puerto Rico", "PRI",
            "PUR"),

    /**
     * North Korea.
     */
    NORTH_KOREA("KP", "North Korea", "Democratic People's Republic of Korea", "조선민주주의인민공화국",
            "Chosŏn Minjujuŭi Inmin Konghwaguk", "Korea, Democratic People's Republic of", "PRK"),

    /**
     * Portugal.
     */
    PORTUGAL("PT", "Portugal", "Portuguese Republic", "Portuguesa", "República Portuguesa", "PRT", "POR"),

    /**
     * Paraguay.
     */
    PARAGUAY("PY", "Paraguay", "Republic of Paraguay", "República del Paraguay", "Tetã Paraguái", "PRY", "PAR"),

    /**
     * Palestine.
     */
    PALESTINE("PS", "Palestine", "State of Palestine", "Palestine, State of", "Dawlat Filasṭin", "PSE", "PLE"),

    /**
     * French Polynesia.
     */
    FRENCH_POLYNESIA("PF", "French Polynesia", "Polynésie française", "Pōrīnetia Farāni", "PYF"),

    /**
     * Qatar.
     */
    QATAR("QA", "Qatar", "State of Qatar", "Dawlat Qaṭar", "QAT"),

    /**
     * Réunion.
     */
    REUNION("RE", "Réunion Island", "Reunion", "REU"),

    /**
     * Romania.
     */
    ROMANIA("RO", "Romania", "Rumania", "Roumania", "România", "ROU"),

    /**
     * Russia.
     */
    RUSSIA("RU", "Russia", "Russian Federation", "Российская Федерация", "RUS"),

    /**
     * Rwanda.
     */
    RWANDA("RW", "Rwanda", "Republic of Rwanda", "Repubulika y'u Rwanda", "République du Rwanda", "RWA"),

    /**
     * Saudi Arabia.
     */
    SAUDI_ARABIA("SA", "Saudi Arabia", "Kingdom of Saudi Arabia", "Saudi", "Al-Mamlakah al-‘Arabiyyah as-Su‘ūdiyyah",
            "SAU", "KSA"),

    /**
     * Sudan.
     */
    SUDAN("SD", "Sudan", "Republic of the Sudan", "Jumhūrīyat as-Sūdān", "SDN", "SUD"),

    /**
     * Senegal.
     */
    SENEGAL("SN", "Senegal", "Republic of Senegal", "République du Sénégal", "SEN"),

    /**
     * Singapore.
     */
    SINGAPORE("SG", "Singapore", "Republic of Singapore", "Singapura", "Republik Singapura", "新加坡共和国", "SGP", "SIN"),

    /**
     * South Georgia.
     */
    SOUTH_GEORGIA("GS", "South Georgia", "South Georgia and the South Sandwich Islands", "SGS"),

    /**
     * Svalbard and Jan Mayen.
     */
    SVALBARD_AND_JAN_MAYEN("SJ", "Svalbard and Jan Mayen", "Svalbard og Jan Mayen", "Svalbard and Jan Mayen Islands",
            "SJM"),

    /**
     * Solomon Islands.
     */
    SOLOMON_ISLANDS("SB", "Solomon Islands", "SLB", "SOL"),

    /**
     * Sierra Leone.
     */
    SIERRA_LEONE("SL", "Sierra Leone", "Republic of Sierra Leone", "SLE"),

    /**
     * El Salvador.
     */
    EL_SALVADOR("SV", "El Salvador", "Republic of El Salvador", "República de El Salvador", "SLV", "ESA"),

    /**
     * San Marino.
     */
    SAN_MARINO("SM", "San Marino", "Most Serene Republic of San Marino", "Republic of San Marino",
            "Repubblica di San Marino", "SMR"),

    /**
     * Somalia.
     */
    SOMALIA("SO", "Somalia", "Federal Republic of Somalia", "aṣ-Ṣūmāl", "Jamhuuriyadda Federaalka Soomaaliya",
            "Jumhūriyyat aṣ-Ṣūmāl al-Fiderāliyya", "SOM"),

    /**
     * Saint Pierre and Miquelon.
     */
    SAINT_PIERRE_AND_MIQUELON("PM", "Saint Pierre and Miquelon",
            "Collectivité territoriale de Saint-Pierre-et-Miquelon", "SPM"),

    /**
     * Serbia.
     */
    SERBIA("RS", "Serbia", "Republic of Serbia", "Srbija", "Република Србија", "Republika Srbija", "SRB"),

    /**
     * South Sudan.
     */
    SOUTH_SUDAN("SS", "South Sudan", "Republic of South Sudan", "SSD"),

    /**
     * São Tomé and Príncipe.
     */
    SAO_TOME_AND_PRINCIPE("ST", "São Tomé and Príncipe", "Democratic Republic of São Tomé and Príncipe",
            "República Democrática de São Tomé e Príncipe", "STP"),

    /**
     * Suriname.
     */
    SURINAME("SR", "Suriname", "Republic of Suriname", "Sarnam", "Sranangron", "Republiek Suriname", "SUR"),

    /**
     * Slovakia.
     */
    SLOVAKIA("SK", "Slovakia", "Slovak Republic", "Slovenská republika", "SVK"),

    /**
     * Slovenia.
     */
    SLOVENIA("SI", "Slovenia", "Republic of Slovenia", "Republika Slovenija", "SVN", "SLO"),

    /**
     * Sweden.
     */
    SWEDEN("SE", "Sweden", "Kingdom of Sweden", "Konungariket Sverige", "SWE"),

    /**
     * Swaziland.
     */
    SWAZILAND("SZ", "Swaziland", "Kingdom of Swaziland", "weSwatini", "Swatini", "Ngwane", "Umbuso waseSwatini", "SWZ"),

    /**
     * Sint Maarten.
     */
    SINT_MAARTEN("SX", "Sint Maarten", "Sint Maarten (Dutch part)", "SXM"),

    /**
     * Seychelles.
     */
    SEYCHELLES("SC", "Seychelles", "Republic of Seychelles", "Repiblik Sesel", "République des Seychelles", "SYC",
            "SEY"),

    /**
     * Syria.
     */
    SYRIA("SY", "Syria", "Syrian Arab Republic", "Al-Jumhūrīyah Al-ʻArabīyah As-Sūrīyah", "SYR"),

    /**
     * Turks and Caicos Islands.
     */
    TURKS_AND_CAICOS_ISLANDS("TC", "Turks and Caicos Islands", "TCA"),

    /**
     * Chad.
     */
    CHAD("TD", "Chad", "Republic of Chad", "Tchad", "République du Tchad", "TCD", "CHA"),

    /**
     * Togo.
     */
    TOGO("TG", "Togo", "Togolese Republic", "Togolese", "République Togolaise", "TGO", "TOG"),

    /**
     * Thailand.
     */
    THAILAND("TH", "Thailand", "Kingdom of Thailand", "Prathet", "Thai", "ราชอาณาจักรไทย", "Ratcha Anachak Thai",
            "THA"),

    /**
     * Tajikistan.
     */
    TAJIKISTAN("TJ", "Tajikistan", "Republic of Tajikistan", "Toçikiston", "Ҷумҳурии Тоҷикистон",
            "Çumhuriyi Toçikiston", "TJK"),

    /**
     * Tokelau.
     */
    TOKELAU("TK", "Tokelau", "TKL"),

    /**
     * Turkmenistan.
     */
    TURKMENISTAN("TM", "Turkmenistan", "TKM"),

    /**
     * Timor-Leste.
     */
    TIMOR_LESTE("TL", "Timor-Leste", "Democratic Republic of Timor-Leste", "East Timor",
            "República Democrática de Timor-Leste", "Repúblika Demokrátika Timór-Leste", "Timór Lorosa'e",
            "Timor Lorosae", "TLS"),

    /**
     * Tonga.
     */
    TONGA("TO", "Tonga", "Kingdom of Tonga", "TON", "TGA"),

    /**
     * Trinidad and Tobago.
     */
    TRINIDAD_AND_TOBAGO("TT", "Trinidad and Tobago", "Republic of Trinidad and Tobago", "TTO"),

    /**
     * Tunisia.
     */
    TUNISIA("TN", "Tunisia", "Tunisian Republic", "Republic of Tunisia", "al-Jumhūriyyah at-Tūnisiyyah", "TUN"),

    /**
     * Turkey.
     */
    TURKEY("TR", "Turkey", "Republic of Turkey", "Turkiye", "Türkiye Cumhuriyeti", "TUR"),

    /**
     * Tuvalu.
     */
    TUVALU("TV", "Tuvalu", "TUV"),

    /**
     * Taiwan.
     */
    TAIWAN("TW", "Taiwan", "Republic of China (Taiwan)", "Táiwān", "Republic of China", "中華民國", "Zhōnghuá Mínguó",
            "Chinese Taipei", "TWN", "TPE"),

    /**
     * Tanzania.
     */
    TANZANIA("TZ", "Tanzania", "United Republic of Tanzania", "Tanzania, United Republic of",
            "Jamhuri ya Muungano wa Tanzania", "TZA", "TAN"),

    /**
     * Uganda.
     */
    UGANDA("UG", "Uganda", "Republic of Uganda", "Jamhuri ya Uganda", "UGA"),

    /**
     * Ukraine.
     */
    UKRAINE("UA", "Ukraine", "Ukrayina", "UKR"),

    /**
     * United States Minor Outlying Islands.
     */
    UNITED_STATES_MINOR_OUTLYING_ISLANDS("UM", "United States Minor Outlying Islands", "UMI"),

    /**
     * Uruguay.
     */
    URUGUAY("UY", "Uruguay", "Oriental Republic of Uruguay", "República Oriental del Uruguay", "URY", "URU"),

    /**
     * United States.
     */
    UNITED_STATES("US", "United States of America", "USA"),

    /**
     * Uzbekistan.
     */
    UZBEKISTAN("UZ", "Uzbekistan", "Republic of Uzbekistan", "O‘zbekiston Respublikasi", "Ўзбекистон Республикаси",
            "UZB"),

    /**
     * Vatican City.
     */
    VATICAN_CITY("VA", "Vatican City", "Vatican City State", "Holy See (Vatican City State)",
            "Stato della Città del Vaticano", "VAT"),

    /**
     * Saint Vincent and the Grenadines.
     */
    SAINT_VINCENT_AND_THE_GRENADINES("VC", "Saint Vincent and the Grenadines", "VCT", "VIN"),

    /**
     * Venezuela.
     */
    VENEZUELA("VE", "Venezuela", "Bolivarian Republic of Venezuela", "Venezuela, Bolivarian Republic of",
            "República Bolivariana de Venezuela", "VEN"),

    /**
     * British Virgin Islands.
     */
    BRITISH_VIRGIN_ISLANDS("VG", "Virgin Islands", "Virgin Islands, British", "VGB", "IVB"),

    /**
     * United States Virgin Islands.
     */
    UNITED_STATES_VIRGIN_ISLANDS("VI", "Virgin Islands of the United States", "Virgin Islands, U.S.", "VIR", "ISV"),

    /**
     * Vietnam.
     */
    VIETNAM("VN", "Vietnam", "Socialist Republic of Vietnam", "Cộng hòa Xã hội chủ nghĩa Việt Nam", "Viet Nam", "VNM",
            "VIE"),

    /**
     * Vanuatu.
     */
    VANUATU("VU", "Vanuatu", "Republic of Vanuatu", "Ripablik blong Vanuatu", "République de Vanuatu", "VUT", "VAN"),

    /**
     * Wallis and Futuna.
     */
    WALLIS_AND_FUTUNA("WF", "Wallis and Futuna", "Territory of the Wallis and Futuna Islands",
            "Territoire des îles Wallis et Futuna", "WLF"),

    /**
     * Samoa.
     */
    SAMOA("WS", "Samoa", "Independent State of Samoa", "Malo Saʻoloto Tutoʻatasi o Sāmoa", "WSM", "SAM"),

    /**
     * Yemen.
     */
    YEMEN("YE", "Yemen", "Republic of Yemen", "Yemeni Republic", "al-Jumhūriyyah al-Yamaniyyah", "YEM"),

    /**
     * South Africa.
     */
    SOUTH_AFRICA("ZA", "South Africa", "Republic of South Africa", "RSA", "Suid-Afrika", "ZAF"),

    /**
     * Zambia.
     */
    ZAMBIA("ZM", "Zambia", "Republic of Zambia", "ZMB", "ZAM"),

    /**
     * Zimbabwe.
     */
    ZIMBABWE("ZW", "Zimbabwe", "Republic of Zimbabwe", "ZWE", "ZIM");

    /**
     * Two character code of the {@link Country}.
     */
    private final String code;

    /**
     * Common name of the {@link Country}.
     */
    private final String name;

    /**
     * Immutable {@link Set} of other aliases for the {@link Country}.
     */
    private final Set<String> aliases;

    /**
     * @param code is the two character code.
     * @param name is the common name.
     * @param aliases is a variable array of aliases.
     */
    Country(String code, String name, String... aliases) {
        this.code = code;
        this.name = name;
        this.aliases = ImmutableSet.copyOf(aliases);
    }

    /**
     * @return the common name of the {@link Country}.
     */
    public String getName() {
        return name;
    }

    /**
     * Internal use only.
     * @return the two character code of the {@link Country}.
     */
    String getCode() {
        return code;
    }

    /**
     * Internal use only.
     * @return an immutable {@link Set} of all the other aliases of the {@link Country}.
     */
    Set<String> getAliases() {
        return aliases;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @JsonValue
    public String toString() {
        return getName();
    }

    /**
     * Parses a {@link String} form of the country, trying to match the two character code, common name or aliases.
     * Parsing is case insensitive.
     *
     * @param value is the value to parse.
     * @return a matching {@link Country} or {@code null} if the input was {@code blank}.
     */
    @JsonCreator
    @Nullable
    static Country parse(@Nullable String value) {
        if (isBlank(value)) {
            return null;
        }
        final String search = value.trim();
        return Stream.of(values()).filter(candidate -> search.equalsIgnoreCase(candidate.getCode())
                || search.equalsIgnoreCase(candidate.getName())
                || candidate.getAliases().stream().anyMatch(alias -> search.equalsIgnoreCase(alias))).findFirst()
                .orElseGet(() -> valueOf(upperCase(search).replace(' ', '_')));
    }
}
