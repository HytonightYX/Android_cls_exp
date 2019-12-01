package me.hy.exp7_gomoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Linux on 2016/4/8.
 */
public class ChessBoardView extends View {
    // 棋盘的宽度，也是长度
    private int mViewWidth;
    // 棋盘每格的长度
    private float maxLineHeight;
    private Paint paint = new Paint();
    // 定义黑白棋子的Bitmap
    private Bitmap mwhitePiece, mblackPiece;
    private float ratioPieceOfLineHeight = 3 * 1.0f / 4;

    // 判断当前落下的棋子是否是白色的
    private boolean mIsWhite = true;
    // 记录黑白棋子位置的列表
    private ArrayList<Point> mwhiteArray = new ArrayList<>();
    private ArrayList<Point> mblackArray = new ArrayList<>();

    // 游戏是否结束
    private boolean mIsGameOver;
    // 游戏结束，是否是白色方胜利
    private boolean mIsWhiteWinner;

    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(0x88000000);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);

        mwhitePiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_w2);
        mblackPiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthModel == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制棋盘的网格
        drawBoard(canvas);
        // 绘制棋盘的黑白棋子
        drawPieces(canvas);
        // 检查游戏是否结束
        checkGameOver();
    }

    // 检查游戏是否结束
    private void checkGameOver() {
        CheckWinner checkWinner = new CheckWinner();
        boolean whiteWin = checkWinner.checkFiveInLineWinner(mwhiteArray);
        boolean blackWin = checkWinner.checkFiveInLineWinner(mblackArray);

        if (whiteWin || blackWin) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

        }
    }

    // 根据黑白棋子的数组绘制棋子
    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mwhiteArray.size(); i < n; i++) {
            Point whitePoint = mwhiteArray.get(i);
            float left = (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;
            float top = (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;

            canvas.drawBitmap(mwhitePiece, left, top, null);
        }

        for (int i = 0, n = mblackArray.size(); i < n; i++) {
            Point blackPoint = mblackArray.get(i);
            float left = (blackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;
            float top = (blackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * maxLineHeight;

            canvas.drawBitmap(mblackPiece, left, top, null);
        }
    }

    // 绘制棋盘的网线
    private void drawBoard(Canvas canvas) {
        int w = mViewWidth;
        float lineHeight = maxLineHeight;

        for (int i = 0; i < Constants.MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, paint);
            canvas.drawLine(y, startX, y, endX, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        maxLineHeight = mViewWidth * 1.0f / Constants.MAX_LINE;

        int pieceWidth = (int) (maxLineHeight * ratioPieceOfLineHeight);
        mwhitePiece = Bitmap.createScaledBitmap(mwhitePiece, pieceWidth, pieceWidth, false);
        mblackPiece = Bitmap.createScaledBitmap(mblackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point point = getValidPoint(x, y);
            if (mwhiteArray.contains(point) || mblackArray.contains(point)) {
                return false;
            }
            if (mIsWhite) {
                mwhiteArray.add(point);
            } else {
                mblackArray.add(point);
            }
            invalidate();
            mIsWhite = !mIsWhite;
        }
        return true;
    }

    private Point getValidPoint(int x, int y) {
        int validX = (int) (x / maxLineHeight);
        int validY = (int) (y / maxLineHeight);

        return new Point(validX, validY);
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);

        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mblackArray);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mwhiteArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mwhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mblackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    // 再来一局
    public void start() {
        mwhiteArray.clear();
        mblackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }
}
