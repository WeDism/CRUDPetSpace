package com.pets_space.models;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

public class EssenceForSearchFriend implements Iterable<String> {
    private String name;
    private String surname;
    private String patronymic;
    private List<String> listParams = new LinkedList<>();
    private StringBuilder resultString = new StringBuilder();


    public EssenceForSearchFriend(HttpServletRequest req) {
        this.name = req.getParameter("name");
        this.surname = req.getParameter("surname");
        this.patronymic = req.getParameter("patronymic");

        if (isNullOrEmpty(this.name) && isNullOrEmpty(this.surname) && isNullOrEmpty(this.patronymic))
            throw new IllegalArgumentException();

        if (!isNullOrEmpty(this.name)) {
            this.resultString.append("LOWER(name)=LOWER(?) ");
            this.listParams.add(this.name);
        }
        if (!isNullOrEmpty(this.surname)) {
            this.resultString.append("LOWER(surname)=LOWER(?) ");
            this.listParams.add(this.surname);
        }
        if (!isNullOrEmpty(this.patronymic)) {
            this.resultString.append("LOWER(patronymic)=LOWER(?)");
            this.listParams.add(this.patronymic);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return listParams.size() > 0;
            }

            @Override
            public String next() {
                if (!this.hasNext())
                    return null;
                return listParams.remove(0);
            }
        };
    }

    public String resultPath() {
        return this.resultString.toString();
    }
}
