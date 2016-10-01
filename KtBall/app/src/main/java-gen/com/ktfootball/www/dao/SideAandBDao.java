package com.ktfootball.www.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.ktfootball.www.dao.SideAandB;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SIDE_AAND_B.
*/
public class SideAandBDao extends AbstractDao<SideAandB, Long> {

    public static final String TABLENAME = "SIDE_AAND_B";

    /**
     * Properties of entity SideAandB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Users = new Property(1, String.class, "users", false, "USERS");
        public final static Property Add_scores = new Property(2, Integer.class, "add_scores", false, "ADD_SCORES");
        public final static Property Result = new Property(3, Integer.class, "result", false, "RESULT");
        public final static Property Goals = new Property(4, Integer.class, "goals", false, "GOALS");
        public final static Property Pannas = new Property(5, Integer.class, "pannas", false, "PANNAS");
        public final static Property Fouls = new Property(6, Integer.class, "fouls", false, "FOULS");
        public final static Property Flagrant_fouls = new Property(7, Integer.class, "flagrant_fouls", false, "FLAGRANT_FOULS");
        public final static Property Panna_ko = new Property(8, Integer.class, "panna_ko", false, "PANNA_KO");
        public final static Property Abstained = new Property(9, Integer.class, "abstained", false, "ABSTAINED");
        public final static Property Users_b = new Property(10, String.class, "users_b", false, "USERS_B");
        public final static Property Add_scores_b = new Property(11, Integer.class, "add_scores_b", false, "ADD_SCORES_B");
        public final static Property Result_b = new Property(12, Integer.class, "result_b", false, "RESULT_B");
        public final static Property Goals_b = new Property(13, Integer.class, "goals_b", false, "GOALS_B");
        public final static Property Pannas_b = new Property(14, Integer.class, "pannas_b", false, "PANNAS_B");
        public final static Property Fouls_b = new Property(15, Integer.class, "fouls_b", false, "FOULS_B");
        public final static Property Flagrant_fouls_b = new Property(16, Integer.class, "flagrant_fouls_b", false, "FLAGRANT_FOULS_B");
        public final static Property Panna_ko_b = new Property(17, Integer.class, "panna_ko_b", false, "PANNA_KO_B");
        public final static Property Abstained_b = new Property(18, Integer.class, "abstained_b", false, "ABSTAINED_B");
        public final static Property Path = new Property(19, String.class, "path", false, "PATH");
        public final static Property Time = new Property(20, String.class, "time", false, "TIME");
        public final static Property Game_type = new Property(21, Integer.class, "game_type", false, "GAME_TYPE");
        public final static Property User_id = new Property(22, String.class, "user_id", false, "USER_ID");
        public final static Property Game_id = new Property(23, String.class, "game_id", false, "GAME_ID");
        public final static Property User1_id = new Property(24, String.class, "user1_id", false, "USER1_ID");
        public final static Property User2_id = new Property(25, String.class, "user2_id", false, "USER2_ID");
        public final static Property League1_id = new Property(26, String.class, "league1_id", false, "LEAGUE1_ID");
        public final static Property League2_id = new Property(27, String.class, "league2_id", false, "LEAGUE2_ID");
        public final static Property Picture = new Property(28, String.class, "picture", false, "PICTURE");
        public final static Property Battle_id = new Property(29, String.class, "battle_id", false, "BATTLE_ID");
    };


    public SideAandBDao(DaoConfig config) {
        super(config);
    }
    
    public SideAandBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SIDE_AAND_B' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'USERS' TEXT," + // 1: users
                "'ADD_SCORES' INTEGER," + // 2: add_scores
                "'RESULT' INTEGER," + // 3: result
                "'GOALS' INTEGER," + // 4: goals
                "'PANNAS' INTEGER," + // 5: pannas
                "'FOULS' INTEGER," + // 6: fouls
                "'FLAGRANT_FOULS' INTEGER," + // 7: flagrant_fouls
                "'PANNA_KO' INTEGER," + // 8: panna_ko
                "'ABSTAINED' INTEGER," + // 9: abstained
                "'USERS_B' TEXT," + // 10: users_b
                "'ADD_SCORES_B' INTEGER," + // 11: add_scores_b
                "'RESULT_B' INTEGER," + // 12: result_b
                "'GOALS_B' INTEGER," + // 13: goals_b
                "'PANNAS_B' INTEGER," + // 14: pannas_b
                "'FOULS_B' INTEGER," + // 15: fouls_b
                "'FLAGRANT_FOULS_B' INTEGER," + // 16: flagrant_fouls_b
                "'PANNA_KO_B' INTEGER," + // 17: panna_ko_b
                "'ABSTAINED_B' INTEGER," + // 18: abstained_b
                "'PATH' TEXT," + // 19: path
                "'TIME' TEXT," + // 20: time
                "'GAME_TYPE' INTEGER," + // 21: game_type
                "'USER_ID' TEXT," + // 22: user_id
                "'GAME_ID' TEXT," + // 23: game_id
                "'USER1_ID' TEXT," + // 24: user1_id
                "'USER2_ID' TEXT," + // 25: user2_id
                "'LEAGUE1_ID' TEXT," + // 26: league1_id
                "'LEAGUE2_ID' TEXT," + // 27: league2_id
                "'PICTURE' TEXT," + // 28: picture
                "'BATTLE_ID' TEXT);"); // 29: battle_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SIDE_AAND_B'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SideAandB entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String users = entity.getUsers();
        if (users != null) {
            stmt.bindString(2, users);
        }
 
        Integer add_scores = entity.getAdd_scores();
        if (add_scores != null) {
            stmt.bindLong(3, add_scores);
        }
 
        Integer result = entity.getResult();
        if (result != null) {
            stmt.bindLong(4, result);
        }
 
        Integer goals = entity.getGoals();
        if (goals != null) {
            stmt.bindLong(5, goals);
        }
 
        Integer pannas = entity.getPannas();
        if (pannas != null) {
            stmt.bindLong(6, pannas);
        }
 
        Integer fouls = entity.getFouls();
        if (fouls != null) {
            stmt.bindLong(7, fouls);
        }
 
        Integer flagrant_fouls = entity.getFlagrant_fouls();
        if (flagrant_fouls != null) {
            stmt.bindLong(8, flagrant_fouls);
        }
 
        Integer panna_ko = entity.getPanna_ko();
        if (panna_ko != null) {
            stmt.bindLong(9, panna_ko);
        }
 
        Integer abstained = entity.getAbstained();
        if (abstained != null) {
            stmt.bindLong(10, abstained);
        }
 
        String users_b = entity.getUsers_b();
        if (users_b != null) {
            stmt.bindString(11, users_b);
        }
 
        Integer add_scores_b = entity.getAdd_scores_b();
        if (add_scores_b != null) {
            stmt.bindLong(12, add_scores_b);
        }
 
        Integer result_b = entity.getResult_b();
        if (result_b != null) {
            stmt.bindLong(13, result_b);
        }
 
        Integer goals_b = entity.getGoals_b();
        if (goals_b != null) {
            stmt.bindLong(14, goals_b);
        }
 
        Integer pannas_b = entity.getPannas_b();
        if (pannas_b != null) {
            stmt.bindLong(15, pannas_b);
        }
 
        Integer fouls_b = entity.getFouls_b();
        if (fouls_b != null) {
            stmt.bindLong(16, fouls_b);
        }
 
        Integer flagrant_fouls_b = entity.getFlagrant_fouls_b();
        if (flagrant_fouls_b != null) {
            stmt.bindLong(17, flagrant_fouls_b);
        }
 
        Integer panna_ko_b = entity.getPanna_ko_b();
        if (panna_ko_b != null) {
            stmt.bindLong(18, panna_ko_b);
        }
 
        Integer abstained_b = entity.getAbstained_b();
        if (abstained_b != null) {
            stmt.bindLong(19, abstained_b);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(20, path);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(21, time);
        }
 
        Integer game_type = entity.getGame_type();
        if (game_type != null) {
            stmt.bindLong(22, game_type);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(23, user_id);
        }
 
        String game_id = entity.getGame_id();
        if (game_id != null) {
            stmt.bindString(24, game_id);
        }
 
        String user1_id = entity.getUser1_id();
        if (user1_id != null) {
            stmt.bindString(25, user1_id);
        }
 
        String user2_id = entity.getUser2_id();
        if (user2_id != null) {
            stmt.bindString(26, user2_id);
        }
 
        String league1_id = entity.getLeague1_id();
        if (league1_id != null) {
            stmt.bindString(27, league1_id);
        }
 
        String league2_id = entity.getLeague2_id();
        if (league2_id != null) {
            stmt.bindString(28, league2_id);
        }
 
        String picture = entity.getPicture();
        if (picture != null) {
            stmt.bindString(29, picture);
        }
 
        String battle_id = entity.getBattle_id();
        if (battle_id != null) {
            stmt.bindString(30, battle_id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SideAandB readEntity(Cursor cursor, int offset) {
        SideAandB entity = new SideAandB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // users
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // add_scores
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // result
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // goals
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // pannas
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // fouls
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // flagrant_fouls
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // panna_ko
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // abstained
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // users_b
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // add_scores_b
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // result_b
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // goals_b
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // pannas_b
            cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15), // fouls_b
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // flagrant_fouls_b
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // panna_ko_b
            cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // abstained_b
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // path
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // time
            cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21), // game_type
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // user_id
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // game_id
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // user1_id
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // user2_id
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // league1_id
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // league2_id
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // picture
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29) // battle_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SideAandB entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsers(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAdd_scores(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setResult(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setGoals(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setPannas(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setFouls(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setFlagrant_fouls(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setPanna_ko(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setAbstained(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setUsers_b(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAdd_scores_b(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setResult_b(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setGoals_b(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setPannas_b(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setFouls_b(cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15));
        entity.setFlagrant_fouls_b(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setPanna_ko_b(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setAbstained_b(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setPath(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTime(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setGame_type(cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21));
        entity.setUser_id(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setGame_id(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setUser1_id(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setUser2_id(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setLeague1_id(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setLeague2_id(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setPicture(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setBattle_id(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SideAandB entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SideAandB entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
