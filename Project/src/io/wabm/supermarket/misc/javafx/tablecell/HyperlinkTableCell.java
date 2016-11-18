package io.wabm.supermarket.misc.javafx.tablecell;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;

/**
 * Created by MainasuK on 2016-11-15.
 *
 * Ref: CheckBoxTableCell
 */
public class HyperlinkTableCell<S> extends TableCell<S, Hyperlink> {

    @Override
    protected void updateItem(Hyperlink item, boolean empty) {
        super.updateItem(item, empty);

        setGraphic(item);
    }

}
