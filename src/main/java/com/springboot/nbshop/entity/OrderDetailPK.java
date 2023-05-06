package com.springboot.nbshop.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailPK implements Serializable {
    private Long product;
    private Integer order;

    public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public OrderDetailPK() {
        super();
    }

    public OrderDetailPK(Long product, Integer order) {
        this.product = product;
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailPK)) return false;
        OrderDetailPK that = (OrderDetailPK) o;
        return Objects.equals(product, that.product) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }
}
