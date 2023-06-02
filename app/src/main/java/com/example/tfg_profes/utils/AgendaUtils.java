package com.example.tfg_profes.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AgendaUtils {
    public static LocalDate selectedDate;
    public static ArrayList<LocalDate> diasEnMesArray (LocalDate date){
        ArrayList<LocalDate> diasEnMesArray= new ArrayList<>();
        YearMonth yearMonth= YearMonth.from(date);
        int diasEnMesNum= yearMonth.lengthOfMonth();
        LocalDate primeroMes=AgendaUtils.selectedDate.withDayOfMonth(1);
        int diaSem= primeroMes.getDayOfWeek().getValue();
        for (int i = 1; i < 42; i++) {
            if (i<= diaSem||i> diasEnMesNum+diaSem){
                diasEnMesArray.add(null);
            }else {
                diasEnMesArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i-diaSem));
            }
        }
        return diasEnMesArray;
    }
    public static ArrayList<LocalDate> diasEnSemArray (LocalDate date){
        ArrayList<LocalDate> diasEnSemArray= new ArrayList<>();
        LocalDate current=sundayForDate(date);
        LocalDate endDate=current.plusWeeks(1);
        while (current.isBefore(endDate)){
            diasEnSemArray.add(current);
            current=current.plusDays(1);
        }
        diasEnSemArray.add(current);
        current.plusDays(1);
        return diasEnSemArray;
    }

    public static LocalDate sundayForDate(LocalDate current) {
        LocalDate unaSemAntes=current.minusWeeks(1);
        while (current.isAfter(unaSemAntes)){
            if (current.getDayOfWeek()== DayOfWeek.MONDAY)
                return current;
            current=current.minusDays(1);
        }
        return null;
    }

    public static String mesAnnoFromDate(LocalDate date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }
}
