package com.chess.engine.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.*;

public class BoardUtils {
    public static boolean[] FIRST_COLOUMN =initColumn(0);
    public static boolean[] SECOND_COLOUMN =initColumn(1);
    public static boolean[] SEVENTH_COLOUMN =initColumn(6);
    public static boolean[] EIGHTH_COLOUMN =initColumn(7);

    public static boolean[] SECOND_ROW = initRow(1);
    public static boolean[] SEVENTH_ROW = initRow(6);

    public static boolean[] EIGHTH_RANK = initRow(0);
    public static boolean[] SEVENTH_RANK = initRow(1);
    public static boolean[] SIXTH_RANK = initRow(2);
    public static boolean[] FIFTH_RANK = initRow(3);
    public static boolean[] FOURTH_RANK = initRow(4);
    public static boolean[] THIRD_RANK = initRow(5);
    public static boolean[] SECOND_RANK = initRow(6);
    public static boolean[] FIRST_RANK = initRow(7);

    public static final List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String,Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();
    public static final int START_TILE_INDEX = 0;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];
        do{
            column[columnNumber]=true;
            columnNumber += NUM_TILES_PER_ROW;
        }while(columnNumber<NUM_TILES);
        return column;
    }
    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[NUM_TILES];
        int id = rowNumber*NUM_TILES_PER_ROW;
        for(int i = 0 ; i<NUM_TILES_PER_ROW; i++){
            row[id + i] = true;
        }
        return row;
    }

    private BoardUtils(){
        throw new RuntimeException("You cannot instantiate me!");
    }
    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    private static List<String> initializeAlgebraicNotation() {
        return ImmutableList.copyOf(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }
    public static boolean isValidTileCoordinate(final int coordinate) {

        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String position){
        return POSITION_TO_COORDINATE.get(position);
    }
    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }
}
