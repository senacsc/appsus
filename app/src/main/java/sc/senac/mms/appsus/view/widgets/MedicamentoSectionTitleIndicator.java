package sc.senac.mms.appsus.view.widgets;

import android.content.Context;
import android.util.AttributeSet;

import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.entity.Medicamento;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;

/**
 * Indicator for sections of type {@link Medicamento}
 */
public class MedicamentoSectionTitleIndicator extends SectionTitleIndicator<String> {

    public MedicamentoSectionTitleIndicator(Context context) {
        super(context);
    }

    public MedicamentoSectionTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MedicamentoSectionTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSection(String character) {
        setTitleText(character);
        setIndicatorBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setIndicatorTextColor(getResources().getColor(R.color.whiteColor));
    }

}