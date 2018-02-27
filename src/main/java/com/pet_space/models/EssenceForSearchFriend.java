package com.pet_space.models;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

public class EssenceForSearchFriend implements Iterable<String> {
    private String name;
    private String surname;
    private String patronymic;
    private List<String> listParams = new LinkedList<>();
    private String resultString;
    private StringBuilder resultStringBuilder = new StringBuilder();
    private final List<Runnable> construct = Arrays.asList(
            () -> {
                if (!isNullOrEmpty(this.name)) {
                    this.resultStringBuilder.append("LOWER(name)=LOWER(?) %1$s ");
                    this.listParams.add(this.name);
                }
            },
            () -> {
                if (!isNullOrEmpty(this.surname)) {
                    this.resultStringBuilder.append("LOWER(surname)=LOWER(?) %1$s ");
                    this.listParams.add(this.surname);
                }
            },
            () -> {
                if (!isNullOrEmpty(this.patronymic)) {
                    this.resultStringBuilder.append("LOWER(patronymic)=LOWER(?)");
                    this.listParams.add(this.patronymic);
                }
            }
    );


    public EssenceForSearchFriend(HttpServletRequest req) {
        this.name = req.getParameter("name");
        this.surname = req.getParameter("surname");
        this.patronymic = req.getParameter("patronymic");

        if (isNullOrEmpty(this.name) && isNullOrEmpty(this.surname) && isNullOrEmpty(this.patronymic))
            throw new IllegalArgumentException();

        construct.forEach(Runnable::run);

        if (this.listParams.size() > 1) this.resultString = String.format(this.resultStringBuilder.toString(), "AND");
        else this.resultString = String.format(this.resultStringBuilder.toString(), "");
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
        return this.resultString;
    }
}
