package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.ComboBoxModel;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class ClassComboBoxModel<T> extends ComboBoxModel<T> {
    String KselectClassinformtion ="select name from classification";

    public ClassComboBoxModel(ComboBox comboBox){
        super(comboBox);
    }

    public ObservableList<T> getList(){
        new WABMThread().run((_void) -> {
            List<String> templist = jdbcOperations.query(KselectClassinformtion,
                    (ResultSet resultSet, int i) -> {
                        String classification = new String(
                                resultSet.getString("name")
                        );
                        return classification;
                    }
            );

            list.clear();
            list.addAll((T[]) templist.toArray());
            String all = new String("全部");
            list.add(0,(T) all);
            return null;
        });
        return list;
    }
    public void resetTaView(){

    }
}
