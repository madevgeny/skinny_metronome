package android.example.toys;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

final class RhythmPopup {

	private final PopupWindow popupWindow;
	private final ViewGroup popupView;

	private final ImageButton r1;
	private final ImageButton r2;
	private final ImageButton r3;
	private final ImageButton r4;

	RhythmPopup(Activity act){
		// inflate the layout of the popup window
		LayoutInflater inflater = (LayoutInflater)act.getSystemService(LAYOUT_INFLATER_SERVICE);
		popupView = (ViewGroup) inflater.inflate(R.layout.rythm_popup, null);

		r1 = popupView.findViewById(R.id.quarterImageButton);
		r2 = popupView.findViewById(R.id.eightImageButton);
		r3 = popupView.findViewById(R.id.tripleImageButton);
		r4 = popupView.findViewById(R.id.sixteenImageButton);

		r1.setImageDrawable(Globals.notesHolder.quarter);
		r2.setImageDrawable(Globals.notesHolder.eight);
		r3.setImageDrawable(Globals.notesHolder.triple);
		r4.setImageDrawable(Globals.notesHolder.sixteen);

		int screenWidth = 900;
		int screenHeight = 400;
		try {
			WindowManager wm = (WindowManager)popupView.getContext().getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics displaymetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(displaymetrics);
			screenWidth = displaymetrics.widthPixels;
			screenHeight = displaymetrics.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		popupWindow = new PopupWindow(popupView, screenWidth, screenHeight / 3, true);

		// dismiss the popup window when touched
		popupView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				popupWindow.dismiss();
				return true;
			}
		});
	}

	void setOnClickListener(final View.OnClickListener listener){
		final View.OnClickListener wrap = v -> {
			listener.onClick(v);
			RhythmPopup.this.dismiss();
		};
		r1.setOnClickListener(wrap);
		r2.setOnClickListener(wrap);
		r3.setOnClickListener(wrap);
		r4.setOnClickListener(wrap);
	}

	void popup(View view) {
		// show the popup window
		// which view you pass in doesn't matter, it is only used for the window token
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	private void dismiss(){
			popupWindow.dismiss();
		}
}
