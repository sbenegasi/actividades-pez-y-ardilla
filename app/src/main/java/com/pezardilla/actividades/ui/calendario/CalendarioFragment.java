package com.pezardilla.actividades.ui.calendario;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.pezardilla.actividades.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;


import java.util.HashSet;

import java.util.Locale;
import java.util.Set;

public class CalendarioFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private TextView tvMonth;
    private ImageButton btnPrev, btnNext;

    private final Set<CalendarDay> surfDays = new HashSet<>();
    private final Set<CalendarDay> pilatesDays = new HashSet<>();

    // Se inicializará más abajo con la Locale del sistema
    private DateTimeFormatter headerFmt;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 0) Referencias UI

        calendarView = view.findViewById(R.id.calendarView);
        tvMonth      = view.findViewById(R.id.tvMonth);
        btnPrev      = view.findViewById(R.id.btnPrev);
        btnNext      = view.findViewById(R.id.btnNext);

        // 1) Locale y formateador de título
        Locale locale = requireContext().getResources()
                .getConfiguration().getLocales().get(0);
        headerFmt = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("es", "ES"));

        // 2) Inicializa calendario en modo mensual y primer día lunes
        calendarView.state().edit()
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setFirstDayOfWeek(DayOfWeek.MONDAY)
                .commit();

        // 3) Formatea cabecera de título y etiquetas de día
        calendarView.setTitleFormatter(day -> {
            // 1) formateas en español
            String texto = day.getDate().format(headerFmt);
            // 2) capitalizas la primera letra
            return texto.substring(0, 1).toUpperCase(new Locale("es", "ES"))
                    + texto.substring(1);
        });

        // 3.2) Le pasamos esa lista al formatter
        CharSequence[] weekLabels = getResources().getTextArray(R.array.week_day_labels_es);
        calendarView.setWeekDayFormatter(
                new com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter(weekLabels)
        );

        // 4) Fechas de ejemplo
        surfDays.add(CalendarDay.from(2024, 7, 22));
        for (int d = 2; d <= 30; d += 7) {
            pilatesDays.add(CalendarDay.from(2024, 9, d));
        }

        // 5) Decoradores
        calendarView.addDecorator(new EventDecorator(
                ContextCompat.getColor(requireContext(), R.color.teal_200), surfDays));
        calendarView.addDecorator(new EventDecorator(
                ContextCompat.getColor(requireContext(), R.color.purple_500), pilatesDays));
        calendarView.addDecorator(new TodayDecorator(
                ContextCompat.getColor(requireContext(), R.color.teal_200)));

        // 6) Selección inicial “hoy”
        CalendarDay today = CalendarDay.today();
        calendarView.setCurrentDate(today);
        calendarView.setDateSelected(today, true);
        updateHeader();

        // 7) Navegación de meses
        btnPrev.setOnClickListener(v -> shiftMonth(-1));
        btnNext.setOnClickListener(v -> shiftMonth(+1));

        // 8) Listener de selección
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date,
                                       boolean selected) {
                if (!selected) return;
                widget.setDateSelected(date, true);
                if (surfDays.contains(date)) {
                    // TODO: detalle Surf
                } else if (pilatesDays.contains(date)) {
                    // TODO: detalle Pilates
                }
            }
        });
    }

    private void updateHeader() {
        CalendarDay cd = calendarView.getCurrentDate();
        tvMonth.setText(cd.getDate().format(headerFmt));
    }

    private void shiftMonth(int delta) {
        CalendarDay cd = calendarView.getCurrentDate();
        LocalDate ld = cd.getDate().plusMonths(delta);
        CalendarDay newCd = CalendarDay.from(
                ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        calendarView.setCurrentDate(newCd);
        calendarView.setDateSelected(newCd, true);
        updateHeader();
    }

    /* ==== Decorators ==== */

    private static class EventDecorator implements
            com.prolificinteractive.materialcalendarview.DayViewDecorator {
        private final int color;
        private final Set<CalendarDay> dates;
        EventDecorator(int color, Set<CalendarDay> dates) {
            this.color = color; this.dates = dates;
        }
        @Override public boolean shouldDecorate(@NonNull CalendarDay day) {
            return dates.contains(day);
        }
        @Override public void decorate(@NonNull
                                       com.prolificinteractive.materialcalendarview.DayViewFacade view) {
            view.addSpan(new android.text.style.ForegroundColorSpan(color));
        }
    }

    private static class TodayDecorator implements
            com.prolificinteractive.materialcalendarview.DayViewDecorator {
        private final CalendarDay today = CalendarDay.today();
        private final int color;
        TodayDecorator(int color) { this.color = color; }
        @Override public boolean shouldDecorate(@NonNull CalendarDay day) {
            return day.equals(today);
        }
        @Override public void decorate(@NonNull
                                       com.prolificinteractive.materialcalendarview.DayViewFacade view) {
            view.addSpan(new android.text.style.StyleSpan(Typeface.BOLD));
            view.addSpan(new android.text.style.ForegroundColorSpan(color));
        }
    }
}
