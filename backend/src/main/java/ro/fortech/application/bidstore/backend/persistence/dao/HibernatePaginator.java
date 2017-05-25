package ro.fortech.application.bidstore.backend.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Order;
import ro.fortech.application.bidstore.backend.model.GenericDTO;

import javax.inject.Named;
import java.util.Collections;

/**
 * Created by robert.ruja on 25-May-17.
 */

@Named
public class HibernatePaginator<T> {

    public GenericDTO<T> getPaginationResults(Criteria criteria, GenericDTO<T> genericDTO) {
        ScrollableResults scrollable = criteria.scroll(ScrollMode.SCROLL_INSENSITIVE);
        if(scrollable.last()){//returns true if there is a resultset
            genericDTO.setTotalCount(scrollable.getRowNumber() + 1);
            criteria.setFirstResult(genericDTO.getFirstElement())
                    .setMaxResults(genericDTO.getElementCount());
            //sorting
            String sortBy = genericDTO.getSortField();
            if(sortBy != null && !sortBy.isEmpty())
                criteria.addOrder(genericDTO.isAscending()? Order.asc(sortBy):Order.desc(sortBy));

            genericDTO.setRows(Collections.unmodifiableList(criteria.list()));
        }
        scrollable.close();
        return genericDTO;
    }
}
