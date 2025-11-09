package com.example.widgetbuddy

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * 위젯의 생명주기 이벤트를 수신하고
 * GlanceAppWidget 인스턴스를 제공한다.
 */
class PetWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = PetWidget()
}
