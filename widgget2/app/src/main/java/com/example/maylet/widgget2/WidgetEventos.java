package com.example.maylet.widgget2;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Esta clase añade al widget al menu de aplicaciones
public class WidgetEventos extends AppWidgetProvider {
    private Handler mHandler  = new Handler();
    RemoteViews views;
    AppWidgetManager appWidgetManager;
    ComponentName currentWidget;
    Context context;
    Calendar cal = Calendar.getInstance();

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {

        this.context = context;
        this.appWidgetManager = appWidgetManager;
        /*pregunta que  elementos existen en el layout*/
        views = new RemoteViews(context.getPackageName(), R.layout.widget);
        currentWidget = new ComponentName(context, WidgetEventos.class);
        mHandler.removeCallbacks(actualizarReloj);
        mHandler.postDelayed(actualizarReloj, 100);
    }
//Actuliza los datos del reloj
    final Runnable actualizarReloj = new Runnable()
    {
        public void run()
        {
            Intent informationIntent = new Intent(context,TabHostPrincipal.class);
            PendingIntent infoPendingIntent = PendingIntent.getActivity(context, 0, informationIntent, 0);
            views.setOnClickPendingIntent(R.id.Widget, infoPendingIntent);
            views.setTextViewText(R.id.widget_textview,getDatos(cal.getTime()));
            appWidgetManager.updateAppWidget(currentWidget, views);
            mHandler.postDelayed(actualizarReloj, 1000);

        }
    };
    //captura y da formato a la fecha del sistema
    public String getDatos(Date date){
        SimpleDateFormat sdf = new
                SimpleDateFormat("h:mmaa");
        sdf.setLenient(false);
        String re = sdf.format(date.getTime());
        return re;
    }

    @Override
    public void onDisabled(Context context)
    {
        super.onDisabled(context);
        mHandler.removeCallbacks(actualizarReloj);
    }
}

