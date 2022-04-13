from tkinter import *
from random import randrange
import math

class PiEstimator(Tk):
    def __init__(self):
        Tk.__init__(self)
        fr = Frame(self)
        fr.pack()

        self.canvas  = Canvas(fr, height = 550, width = 500)
        self.canvas.pack()

        self.canvas.create_rectangle(50, 50, 450, 450)
        self.canvas.create_oval(50, 50, 450, 450)

        incirc = Label(self.canvas, text='Points in circle:')
        incirc.place(x=40, y=500)
        self.inside = IntVar()
        self.inside.set(0)
        pts_in = Label(self.canvas, textvariable=self.inside)
        pts_in.place(x=128, y=500)

        outcirc = Label(self.canvas, text='Points out circle:')
        outcirc.place(x=160, y=500)
        self.outside = IntVar()
        self.outside.set(0)
        pts_out = Label(self.canvas, textvariable=self.outside)
        pts_out.place(x=250, y=500)

        pi_est = Label(self.canvas, text='Pi estimate:')
        pi_est.place(x=280, y=500)
        self.pi = StringVar()
        self.pi.set("0")
        pi_is = Label(self.canvas, textvariable=self.pi)
        pi_is.place(x=350, y=500)
    
        self.in_circle = 0
        self.out_circle = 0
        self.repeat()

    def repeat(self):
        if self.in_circle + self.out_circle >= 10000:
            return
        x = randrange(400) + 50
        y = randrange(400) + 50
        xlen = abs(x - 250)
        ylen = abs(y - 250)
        dist = math.sqrt(xlen*xlen + ylen*ylen)
        if (dist <= 200):
            self.in_circle += 1
            self.inside.set(self.in_circle)
        else:
            self.out_circle += 1
            self.outside.set(self.out_circle)
        self.pi.set(str(4*self.in_circle/(self.in_circle + self.out_circle)))
        self.canvas.create_oval(x, y, x+1, y+1, fill="red", outline="red")
        self.after(10, self.repeat)

if __name__ == "__main__":
    root = PiEstimator()
    root.mainloop()
