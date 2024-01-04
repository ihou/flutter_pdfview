package io.endigo.plugins.pdfviewflutter;

import android.content.Context;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.lang.reflect.Field;

public class MyScrollHandle extends DefaultScrollHandle {
    private boolean inverted;

    public MyScrollHandle(Context context) {
        super(context);
    }

    public MyScrollHandle(Context context, boolean inverted) {
        super(context, inverted);
        this.inverted = inverted;
    }

    @Override
    public void setupLayout(PDFView pdfView) {
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.setMargins(0, 0, 0, 0);
        if (pdfView.isSwipeVertical()) {
            lp.addRule(inverted ? ALIGN_PARENT_LEFT : ALIGN_PARENT_RIGHT);
            setBackgroundResource(R.drawable.pdf_handle_vertical);
        } else {
            lp.addRule(inverted ? ALIGN_PARENT_TOP : ALIGN_PARENT_BOTTOM);
            setBackgroundResource(R.drawable.pdf_handle_horizontal);
        }
        pdfView.addView(this, lp);
        try {
            Field field = DefaultScrollHandle.class.getDeclaredField("pdfView");
            field.setAccessible(true);
            field.set(this, pdfView);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
