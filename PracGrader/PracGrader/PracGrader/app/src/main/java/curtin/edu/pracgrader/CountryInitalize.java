package curtin.edu.pracgrader;

public class CountryInitalize
{
    int[] images;
    String[] names;

    public CountryInitalize()
    {
        images = new int[] {R.drawable.flag_ad
                , R.drawable.flag_ae
                , R.drawable.flag_af
                , R.drawable.flag_ag
                , R.drawable.flag_ai
                , R.drawable.flag_al
                , R.drawable.flag_am
                , R.drawable.flag_ar
                , R.drawable.flag_at
                , R.drawable.flag_au
                , R.drawable.flag_az
                , R.drawable.flag_ba
                , R.drawable.flag_bd
                , R.drawable.flag_be
                , R.drawable.flag_bf
                , R.drawable.flag_bg
                , R.drawable.flag_br
                , R.drawable.flag_ca
                , R.drawable.flag_ch
                , R.drawable.flag_cn
                , R.drawable.flag_cz
                , R.drawable.flag_de
                , R.drawable.flag_dk
                , R.drawable.flag_es
                , R.drawable.flag_fr
                , R.drawable.flag_gb
                , R.drawable.flag_ge
                , R.drawable.flag_gr
                , R.drawable.flag_hk
                , R.drawable.flag_it
                , R.drawable.flag_jp
                , R.drawable.flag_kr
                , R.drawable.flag_lt
                , R.drawable.flag_mx
                , R.drawable.flag_my
                , R.drawable.flag_nl
                , R.drawable.flag_pl
                , R.drawable.flag_qa
                , R.drawable.flag_ru
                , R.drawable.flag_uk
                , R.drawable.flag_us
                , R.drawable.flag_vn};

        names = new String[]
                {"Andorra"
                        , "United Arab Emirates"
                , "Afghanistan"
                , "Antigua and Barbuda"
                , "Anguilla"
                , "Albania"
                , "Armenia"
                , "Argentina"
                , "Austria"
                , "Australia"
                , "Azerbaijan"
                , "Bosnia and Herzegovina"
                , "Bangladesh"
                        , "Belgium"
                , "Burkina Faso"
                , "Bulgaria"
                , "Brazil"
                , "Canada"
                , "Switzerland"
                , "China"
                , "Czeck Republic"
                , "Germany"
                , "Denmark"
                , "Spain"
                , "France"
                , "Great Britain"
                , "Georgia"
                , "Greece"
                , "Hong Kong"
                , "Italy"
                , "Japan"
                , "South Korea"
                , "Lithuania"
                , "Mexico"
                , "Malaysia"
                , "Netherlands"
                , "Poland"
                , "Qatar"
                , "Russia"
                , "United Kingdom"
                , "United States of America"
                , "Vietnam"};
    }

    public int[] getImages()
    {
        return images;
    }

    public String[] getNames()
    {
        return names;
    }

    public int getNamePosition(String inName)
    {
        int count = 0;
        int returnCount = 0;

        for(int i = 0; i < names.length; i++)
        {
            if(names[i].equals(inName))
            {
                returnCount = i;
            }
            else
            {
                count++;
            }
        }

        return returnCount;
    }

    public int getImage(String name)
    {
        int i = getNamePosition(name);

        return images[i];
    }
}
