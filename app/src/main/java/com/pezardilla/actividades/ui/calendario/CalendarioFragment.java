package com.pezardilla.actividades.ui.calendario;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.pezardilla.actividades.R;

import java.util.HashSet;
import java.util.Set;

public class CalendarioFragment extends Fragment {

    private MaterialCalendarView calendarView;

    // Conjuntos de días para decorar
    private final Set<CalendarDay> surfDays = new HashSet<>();
    private final Set<CalendarDay> pilatesDays = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.calendarView);

        // 1) Prepara fechas de ejemplo:
        surfDays.add(CalendarDay.from(2024, 7, 22));  // Julio 22, 2024
        for (int d = 2; d <= 30; d += 7) {
            pilatesDays.add(CalendarDay.from(2024, 9, d)); // Lunes de septiembre
        }

        // 2) Añade decoradores para marcar días
        calendarView.addDecorator(new EventDecorator(
                ContextCompat.getColor(requireContext(), R.color.teal_200),
                surfDays
        ));
        calendarView.addDecorator(new EventDecorator(
                ContextCompat.getColor(requireContext(), R.color.purple_500),
                pilatesDays
        ));

        // 3) Maneja selección de fecha
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date,
                                       boolean selected) {
                if (surfDays.contains(date)) {
                    widget.setSelectedDate(date);
                    // TODO: mostrar detalle de Surf
                } else if (pilatesDays.contains(date)) {
                    widget.setSelectedDate(date);
                    // TODO: mostrar detalle de Pilates
                }
            }
        });

        // 4) Selecciona hoy por defecto
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.addDecorator(new TodayDecorator(
                ContextCompat.getColor(requireContext(), R.color.teal_200)
        ));

    }

    // Decorator para colorear el texto de la fecha
    private static class EventDecorator implements DayViewDecorator {
        private final int color;
        private final Set<CalendarDay> dates;

        EventDecorator(int color, Set<CalendarDay> dates) {
            this.color = color;
            this.dates = dates;
        }

        @Override
        public boolean shouldDecorate(@NonNull CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(@NonNull DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(color));
        }
    }

    private static class TodayDecorator implements DayViewDecorator {
        private final CalendarDay today = CalendarDay.today();
        private final int color;
        TodayDecorator(int color) { this.color = color; }
        @Override public boolean shouldDecorate(@NonNull CalendarDay day) {
            return day.equals(today);
        }
        @Override public void decorate(@NonNull DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new ForegroundColorSpan(color));
        }
    }
}
