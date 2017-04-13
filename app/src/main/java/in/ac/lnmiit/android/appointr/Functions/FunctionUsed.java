package in.ac.lnmiit.android.appointr.Functions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionUsed {


    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static long getLongFromDate(String stringDate){
        try {
            Date d = (Date) simpleDateFormat.parse(stringDate);
            return  d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getStringFromDate(long longDate){
        Date date=new Date(longDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getDateTtimeSringFromDate(long longDate){
        Date date=new Date(longDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa, dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getTimeSringFromDate(long longDate){
        Date date=new Date(longDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String getTimeFromDate(long longDate){
        Date date=new Date(longDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
        return simpleDateFormat.format(date);
    }





}
