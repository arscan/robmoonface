package com.robscanlon.android.robmoonface;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class RobMoonFace extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new RobMoonFaceEngine();
	}

	private class RobMoonFaceEngine extends Engine {
		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}

		};
		private Paint paint = new Paint();
		private int width;
		int height;
		private boolean visible = true;
		private Bitmap bg;
		private Bitmap fg;
        private long mStartTime;
        private float mOffset;

		public RobMoonFaceEngine() {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(RobMoonFace.this);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			handler.post(drawRunner);
			bg = BitmapFactory.decodeResource(getResources(), R.drawable.xbackground);
			fg = BitmapFactory.decodeResource(getResources(), R.drawable.xforeground);

            mStartTime = SystemClock.elapsedRealtime();
            mOffset=50;
            	
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		

		@Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                float xStep, float yStep, int xPixels, int yPixels) {
            mOffset = xOffset;
            draw();
        }
		
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			this.width = width;
			this.height = height;
			super.onSurfaceChanged(holder, format, width, height);
		}


		private void draw() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					

					Matrix mat = new Matrix();
		            float xrot = ((float)(SystemClock.elapsedRealtime() - mStartTime)) / 400;
					mat.postTranslate(-500,-500);
		            mat.postRotate(xrot,(float)(width/1.7),(float)(height/1.7));
					canvas.drawBitmap(bg,mat, paint);
					canvas.drawBitmap(fg,-mOffset*500,0,paint);
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 15);
			}
		}

	}
}