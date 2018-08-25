package com.mpoznyak.data.specification.types;

import com.mpoznyak.domain.repository.Specification;

import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.COLUMN_LAST_OPENED;
import static com.mpoznyak.data.DatabaseHelper.DatabaseContract.TABLE_TYPES;

/**
 * Created by Max Poznyak on 23.08.2018.
 */
public class LastOpenedTypeSpecification implements Specification {

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + TABLE_TYPES + " WHERE " + COLUMN_LAST_OPENED + " = 1" + ";";
    }
}
