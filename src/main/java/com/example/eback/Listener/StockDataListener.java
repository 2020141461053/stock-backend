package com.example.eback.Listener;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.example.eback.WebSocket.WebSocket;
@Component
@AllArgsConstructor
public class StockDataListener implements ApplicationListener<StockDataEvent> {
    @Override
    @EventListener(StockDataEvent.class)
    public void onApplicationEvent(StockDataEvent event) {
        try {
            WebSocket.sendInfo(event.getStockData());
                  }  catch (Exception e) {
            e.printStackTrace();
        }

    }
}
