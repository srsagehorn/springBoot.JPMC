package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kylerudy
 */
public interface RoomDao {
    List<Room> getAllRooms();
    Room getRoomById(int id);
    Room addRoom(Room room);
    void updateRoom(Room room);
    void deleteRoomById(int id);

    @Repository
    public class RoomDaoDB implements RoomDao{
        @Autowired
        JdbcTemplate jdbc;

        public static final class RoomMapper implements RowMapper<Room> {

            @Override
            public Room mapRow(ResultSet rs, int index) throws SQLException {
                Room rm = new Room();
                rm.setId(rs.getInt("id"));
                rm.setName(rs.getString("name"));
                rm.setDescription(rs.getString("description"));
                return rm;
            }
    }

    @Override
    public List<Room> getAllRooms() {
        final String SELECT_ALL_ROOMS = "SELECT * FROM room";
        return jdbc.query(SELECT_ALL_ROOMS, new RoomMapper());

        }
    }

    @Override
    public Room getRoomById(int id) {
        try {
            final String SELECT_ROOM_BY_ID = "SELECT * FROM room WHERE id = ?";
            return jdbc.queryForObject(SELECT_ROOM_BY_ID, new RoomDaoDB.RoomMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }
}

