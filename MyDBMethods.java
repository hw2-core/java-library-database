/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */

package hw2.java.library.database;

import java.util.List;

public class MyDBMethods {

    /**
     * Fix per tutti i caratteri speciali di una query sql
     *
     * @param stringa la stringa che bisogna processare
     * @return la stringa con le adeguate sostituzioni
     */
    public static String fixSqlString(String stringa) {
		// controllare l'esistenza di metodi preesistenti
        //XXX previene il crash durante l'esecuzione di una query di ricerca con apostrofo
        stringa = stringa.replace("'", "''");
        //XXX esclude la possibilita' di utilizzare gli altri caratteri speciali SQL durante la ricerca
        stringa = stringa.replace("_", "\\_");
        stringa = stringa.replace("%", "\\%");

        return stringa;
    }

    /**
     * Da utilizzare per creare i parametri di una query su tabelle relazionali
     *
     * @param sql query di inserimento da completare
     * @param list lista che contiene i dati da inserire
     */
    public static <T> String prepareSqlRelationParameters(String sql, List<? super T> list) {
        int i = 0;
        for (i = 0; i < list.size(); i++) {
            // crea la lista di parametri per lo statement
            sql = sql.concat(" ( ? , ? )");
            // inserisce la virgola fino al penultimo loop
            if (i + 1 < list.size()) {
                sql = sql.concat(",");
            }
        }
        // chiude la query
        sql = sql.concat(";");
        return sql;
    }

    // use polymorphism as "switcher" for search condition
    // case String:
    public static String getSearchCond(String field, String searchVal) {
        searchVal = MyDBMethods.fixSqlString(searchVal);
        return " "+field+" LIKE '%" + searchVal + "%'";
    }
    
    // default:
    public static <T> String getSearchCond(String field, T searchVal) {
        return " "+field+" = " + searchVal;
    }

}
