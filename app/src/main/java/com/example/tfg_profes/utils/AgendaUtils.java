package com.example.tfg_profes.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AgendaUtils {
    public static LocalDate selectedDate;
    public static ArrayList<String> diasEnMesArray (LocalDate date){
        ArrayList<String> diasEnMesArray= new ArrayList<>();
        YearMonth yearMonth= YearMonth.from(date);
        int diasEnMesNum= yearMonth.lengthOfMonth();
        LocalDate primeroMes=AgendaUtils.selectedDate.withDayOfMonth(1);
        int diaSem= primeroMes.getDayOfWeek().getValue();
        for (int i = 1; i < 42; i++) {
            if (i<= diaSem||i> diasEnMesNum+diaSem){
                diasEnMesArray.add("");
            }else {
                diasEnMesArray.add(String.valueOf(i-diaSem));
            }
        }
        return diasEnMesArray;
    }
    public static ArrayList<LocalDate> diasEnSemArray (LocalDate date){
        ArrayList<LocalDate> diasEnSemArray= new ArrayList<>();
        LocalDate current=sundayForDate(selectedDate);
        /*LocalDate endDate=current.plusYears(3);
        while (current.isBefore(endDate)){
            diasEnSemArray.add(current);
            current.plusDays(1);
        }*/
        diasEnSemArray.add(current);
        current.plusDays(1);
        return diasEnSemArray;
    }

    public static LocalDate sundayForDate(LocalDate current) {
        LocalDate unaSemAntes=current.minusWeeks(1);
        while (current.isAfter(unaSemAntes)){
            if (current.getDayOfWeek()== DayOfWeek.SUNDAY)
                return current;
            current=current.minusDays(1);
        }
        return null;
    }

    public static String mesAnnoFromDate(LocalDate date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
}
